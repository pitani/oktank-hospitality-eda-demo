package com.oktank.reservation;

import lombok.*;
import java.math.BigDecimal;
import java.time.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ReservationCreated {
  private String reservationId;
  private String guestId;
  private String hotelId;
  private String roomType;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private BigDecimal amount;
  private String currency;
  private Instant createdAt;
}