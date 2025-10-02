package com.tanmay.e_commerce_application.payment_service.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.tanmay.e_commerce_application.payment_service.DTO.Event.PaymentEventDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Request.OrderItemRequestDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Response.OrderResponseDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Response.PaymentDetailsResponseDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.payment_service.Entity.Payment;
import com.tanmay.e_commerce_application.payment_service.Enums.PaymentStatus;
import com.tanmay.e_commerce_application.payment_service.FeignClients.OrderClient;
import com.tanmay.e_commerce_application.payment_service.Repository.PaymentRepo;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {

    @Value("${stripe.secret}")
    private String stripeKey;

    @Value("${stripe.url.success}")
    private String successUrl;

    @Value("${stripe.url.cancel}")
    private String cancelUrl;

    @Value("${stripe.expire}")
    private Integer expireTime;

    @Value("${stripe.currency}")
    private String currency;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @Autowired
    private PaymentRepo pRepo;

    @PostConstruct
    public void init(){
        Stripe.apiKey = stripeKey;
    }

    @Autowired
    private OrderClient oClient;

    @Autowired
    private KafkaTemplate<String, PaymentEventDTO> kafkaTemplate;

    public PaymentDetailsResponseDTO checkout(List<OrderItemRequestDTO> oDto, String userId) throws StripeException {
        ResponseEntity<ApiResponseWrapper<OrderResponseDTO>> resp = oClient.createOrder(oDto, userId);
        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null){
            throw new RuntimeException("Service failure");
        }

        OrderResponseDTO oResponseDTO = resp.getBody().getPayLoad();

        SessionCreateParams.Builder sBuilder = SessionCreateParams.builder();

        oResponseDTO.getOrderItems().forEach(oi -> {
            sBuilder.addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(currency)
                            .setUnitAmount(oi.getPrice().longValue() * 100)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(oi.getName())
                                    .build()
                            )
                            .build()    
                    )
                    .setQuantity(oi.getQuantity().longValue())
                    .build()  
            );
        });
        
        sBuilder.setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .setCancelUrl(cancelUrl)
            .setExpiresAt(Instant.now().getEpochSecond() + expireTime)
            .setPaymentIntentData(
                SessionCreateParams.PaymentIntentData.builder()
                    .putMetadata("Order-Id", oResponseDTO.getId().toString())
                    .putMetadata("User-Id", userId)
                    .build()
            );
        
        Session paymentLink = Session.create(sBuilder.build());
        Payment payment = Payment.builder()
            .amount(oResponseDTO.getAmount())
            .orderId(oResponseDTO.getId())
            .status(PaymentStatus.PENDING)
            .build();

        pRepo.save(payment);

        return PaymentDetailsResponseDTO.builder()
            .paymentUrl(paymentLink.getUrl())
            .build();
    }

    public void handeEvents(String payload, String signHeader) throws SignatureVerificationException{
        Event event = Webhook.constructEvent(payload, signHeader, webhookSecret);
        EventDataObjectDeserializer dataDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = dataDeserializer.getObject().orElseThrow();
        
        switch (event.getType()) {
            case "payment_intent.succeeded":
                updatePayment(stripeObject, PaymentStatus.SUCCESS);
                break;
            
            case "payment_intent.payment_failed":
                updatePayment(stripeObject, PaymentStatus.FAILED);
                break;

            default:
                break;
        }
    }

    public void updatePayment(StripeObject stripeObject, PaymentStatus status){
        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        UUID orderId = UUID.fromString(paymentIntent.getMetadata().get("Order-Id"));
        pRepo.findByOrderId(orderId)
            .ifPresent(payment -> {
                payment.setStatus(status);
                payment.setTransactionId(paymentIntent.getId());
                pRepo.save(payment);
            });

        PaymentEventDTO pDto = PaymentEventDTO.builder()
            .orderId(paymentIntent.getMetadata().get("Order-Id"))
            .userId(paymentIntent.getMetadata().get("User-Id"))
            .build();

        if(status == PaymentStatus.SUCCESS){
            kafkaTemplate.send("PAYMENT.SUCCESS", pDto);
        } else{
            kafkaTemplate.send("PAYMENT.FAILED", pDto);
        }
    }
}
