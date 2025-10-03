package com.tanmay.e_commerce_application.order_service.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanmay.e_commerce_application.order_service.DTO.Event.PaymentEvent;
import com.tanmay.e_commerce_application.order_service.Enums.Status;
import com.tanmay.e_commerce_application.order_service.Repository.OrderRepo;

@Service
public class UpdateOrderService {

    @Autowired
    private OrderRepo orderRepo;

    @KafkaListener(groupId = "ecommerce", topics = "PAYMENT.SUCCESS")
    public void updateOrderStatus(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        PaymentEvent paymentEvent = mapper.readValue(message, PaymentEvent.class);

        orderRepo.findByIdAndUserId(UUID.fromString(paymentEvent.getOrderId()), UUID.fromString(paymentEvent.getUserId())).ifPresent(o -> {
            o.setOrderStatus(Status.PAID);
            orderRepo.save(o);
        });
    }
}
