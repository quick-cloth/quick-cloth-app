package org.example.quickclothapp.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMinimumStockRequest {
    private UUID clotheUuid;
    private UUID inventoryUuid;
    private Integer minimumStock;
}