package org.example.quickclothapp.payload.response;

import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponse {
    private UUID uuid;
    private Integer stock;
    private Integer minimumStock;
    private UUID clotheUuid;
    private String clotheName;
    private String typeGenderName;
    private String typeStageName;
    private BigInteger unitPrice;
}
