package org.example.quickclothapp.payload.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleListResponse {
    private String clotheUuid;
    private String clotheName;
    private String typeGenderName;
    private String typeStageName;
    private Integer quantity;
    private Double value;
    private List <CampaignResponse> campaignList = new ArrayList<>();
}
