package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DonationRequest {
    private UUID clotheBankUuid;
    private UUID typeClotheUuid;
    private UUID typeGenderUuid;
    private UUID typeStageUuid;
    private UUID userUuid;
    private Integer quantity;
}
