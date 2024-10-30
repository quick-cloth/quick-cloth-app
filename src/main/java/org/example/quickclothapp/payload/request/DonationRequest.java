package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DonationRequest {
    private UUID userUuid;
    private UUID clotheBankUuid;
    private List<ClotheDonationRequest> clothesDonation;
}
