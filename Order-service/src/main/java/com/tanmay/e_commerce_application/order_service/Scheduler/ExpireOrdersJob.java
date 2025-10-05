package com.tanmay.e_commerce_application.order_service.Scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tanmay.e_commerce_application.order_service.DTO.Event.ExpiredOrderEvent;
import com.tanmay.e_commerce_application.order_service.Entity.Order;
import com.tanmay.e_commerce_application.order_service.Enums.Status;
import com.tanmay.e_commerce_application.order_service.Repository.OrderRepo;

@Component
public class ExpireOrdersJob {

    @Autowired
    private OrderRepo oRepo;

    @Autowired
    private KafkaTemplate<String, ExpiredOrderEvent> kafkaTemplate;

    @Scheduled(cron = "0 */1 * * * ?")
    public void expireOrders(){
        LocalDateTime now = LocalDateTime.now();
        List<Order> expiredOrders = new ArrayList<>();
        Set<UUID> expiredOrderIds = new HashSet<>();
        Map<UUID, Integer> mapVariantIdVsQuantity = new HashMap<>();
        System.out.println("order expiry job:");
        oRepo.findExpiredOrders(Status.PENDING, now.minus(30, ChronoUnit.MINUTES)).forEach(o -> {
            o.setOrderStatus(Status.EXPIRED);
            expiredOrders.add(o);
            expiredOrderIds.add(o.getId());

            o.getOrderItems().forEach(oi -> {
                Integer quantity = oi.getQuantity();
                if(mapVariantIdVsQuantity.containsKey(oi.getProductVariantId())){
                    quantity += mapVariantIdVsQuantity.get(oi.getProductVariantId());
                }
                mapVariantIdVsQuantity.put(oi.getProductVariantId(), quantity);
            });
        });
        if(!expiredOrderIds.isEmpty()){
            oRepo.saveAll(expiredOrders);
            ExpiredOrderEvent eOrderEvent = ExpiredOrderEvent.builder()
                .expiredOrders(expiredOrderIds)
                .variantIdVsQuantity(mapVariantIdVsQuantity)
                .build();
            System.out.println(expiredOrderIds);
            System.out.println(mapVariantIdVsQuantity);
            System.out.println(eOrderEvent);
            kafkaTemplate.send("ORDERS.EXPIRED", eOrderEvent);
        }
    }
}
