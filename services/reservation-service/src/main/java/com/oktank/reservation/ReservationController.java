package com.oktank.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
  private final ReservationProducer producer;

  @PostMapping
  public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ReservationCreated req) {
    req.setCreatedAt(Instant.now());
    producer.publish(req);
    return ResponseEntity.accepted().body(Map.of("status","PUBLISHED","reservationId", req.getReservationId()));
  }
}