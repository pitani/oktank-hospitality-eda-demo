package com.oktank.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationProducer {
  private final KafkaTemplate<String, ReservationCreated> kafkaTemplate;
  @Value("${app.topics.reservationCreated}") private String topic;

  public void publish(ReservationCreated evt) {
    kafkaTemplate.send(topic, evt.getReservationId(), evt);
  }
}