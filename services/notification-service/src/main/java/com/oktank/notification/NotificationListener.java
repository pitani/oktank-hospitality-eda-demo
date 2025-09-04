// src/main/java/com/oktank/notification/NotificationListener.java
package com.oktank.notification;

import com.oktank.reservation.ReservationCreated;
import com.oktank.loyalty.LoyaltyUpdated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

  @KafkaListener(topics = "${app.topics.reservationCreated}", groupId = "notification-svc")
  public void onReservation(ReservationCreated evt) {
    String hotel = evt.getHotelId() != null ? evt.getHotelId() : "unknown-hotel";
    log.info("Email: Booking confirmed for guest {} at hotel {}.", evt.getGuestId(), hotel);
  }

  @KafkaListener(topics = "${app.topics.loyaltyUpdated}", groupId = "notification-svc")
  public void onLoyalty(LoyaltyUpdated evt) {
    String resId = evt.getReservationId() != null ? evt.getReservationId() : "unknown-reservation";
    log.info("SMS: {} points earned for reservation {}.", evt.getPointsEarned(), resId);
  }
}
