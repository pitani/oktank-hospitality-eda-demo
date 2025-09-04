// src/main/java/com/oktank/loyalty/LoyaltyUpdated.java
package com.oktank.loyalty;

import java.time.Instant;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyUpdated {
  private String reservationId;
  private String guestId;
  private int pointsEarned;
  private Instant timestamp;
}
