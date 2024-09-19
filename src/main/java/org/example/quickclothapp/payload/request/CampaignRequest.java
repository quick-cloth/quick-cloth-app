package org.example.quickclothapp.payload.request;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CampaignRequest {
    private String name;
    private String message_campaign;
    private LocalDate creation_date;
    private LocalDate end_date;
    private UUID clotheBankUuid;
    private UUID typeCampaignUuid;
    private UUID typeClotheUuid;
    private UUID typeGenderUuid;
    private UUID typeStageUuid;
    private Integer discount;
}
