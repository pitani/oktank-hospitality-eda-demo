// src/main/java/com/oktank/loyalty/LoyaltyListener.java
package com.oktank.loyalty;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.oktank.reservation.ReservationCreated;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoyaltyListener {

  private final KafkaTemplate<String, LoyaltyUpdated> kafkaTemplate;
  private final String loyaltyUpdatedTopic;

  public LoyaltyListener(
      KafkaTemplate<String, LoyaltyUpdated> kafkaTemplate,
      @Value("${app.topics.loyaltyUpdated}") String loyaltyUpdatedTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.loyaltyUpdatedTopic = loyaltyUpdatedTopic;
  }

  @KafkaListener(
      topics = "${app.topics.reservationCreated}",
      groupId = "${spring.kafka.consumer.group-id}"
  )
  public void onReservation(@Payload ReservationCreated rc) {
    log.info("Loyalty received: {}", rc);

    BigDecimal amount = rc != null ? rc.getAmount() : null;
    int points = amount != null ? amount.intValue() : 0;

    LoyaltyUpdated evt = new LoyaltyUpdated(
        rc.getReservationId(),
        rc.getGuestId(),
        points,
        Instant.now()
    );

    // use reservationId as the record key
    kafkaTemplate.send(loyaltyUpdatedTopic, rc.getReservationId(), evt);
    log.info("Loyalty emitted: {}", evt);
  }
}
