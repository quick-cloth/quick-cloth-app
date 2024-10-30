package org.example.quickclothapp.payload.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CampaignResponse {
    private UUID uuid;
    private String campaignName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private double discount;
    private double valueDiscount;
}
