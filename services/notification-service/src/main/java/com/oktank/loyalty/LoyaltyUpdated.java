package com.oktank.loyalty;

import lombok.*;
import java.time.Instant;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class LoyaltyUpdated {
  private String reservationId;
  private String guestId;
  private int pointsEarned;
  private Instant timestamp;
}