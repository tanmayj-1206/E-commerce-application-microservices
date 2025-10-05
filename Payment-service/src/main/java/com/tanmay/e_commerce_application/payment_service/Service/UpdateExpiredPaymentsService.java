package com.tanmay.e_commerce_application.payment_service.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanmay.e_commerce_application.payment_service.DTO.Event.ExpiredOrdersEvent;
import com.tanmay.e_commerce_application.payment_service.Entity.Payment;
import com.tanmay.e_commerce_application.payment_service.Enums.PaymentStatus;
import com.tanmay.e_commerce_application.payment_service.Repository.PaymentRepo;

import jakarta.transaction.Transactional;

@Service
public class UpdateExpiredPaymentsService {

    @Autowired
    private PaymentRepo pRepo;

    @Transactional
    @KafkaListener(topics = "ORDERS.EXPIRED")
    public void updateExpiredPayments(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        ExpiredOrdersEvent eOrdersEvent = mapper.readValue(message, ExpiredOrdersEvent.class);
        List<Payment> expiredPayments = new ArrayList<>();

        pRepo.findAllByOrderIdIn(eOrdersEvent.getExpiredOrders()).forEach(p -> {
            p.setStatus(PaymentStatus.EXPIRED);
            expiredPayments.add(p);
        });

        pRepo.saveAll(expiredPayments);
    }
}
