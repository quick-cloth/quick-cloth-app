package org.example.quickclothapp.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CampaignResponse {
    private String campaignName;
    private String description;
    private double discount;
    private double valueDiscount;
}
