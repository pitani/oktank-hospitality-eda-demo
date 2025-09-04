package com.oktank.housekeeping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.oktank.reservation.ReservationCreated;

@Component
@Slf4j
public class HousekeepingListener {
  @KafkaListener(topics = "${app.topics.reservationCreated}", groupId = "housekeeping-svc")
  public void onReservation(ReservationCreated evt) {
    log.info("Scheduled cleaning task for roomType={} stay={}→{}", evt.getRoomType(), evt.getCheckIn(), evt.getCheckOut());
  }
}