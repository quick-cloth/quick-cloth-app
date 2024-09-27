package org.example.quickclothapp.payload.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DonationResponse {
    private UUID uuid;
    private String donorName;
    private LocalDate donationDate;
    private Integer quantity;
}
