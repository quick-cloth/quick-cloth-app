package org.example.quickclothapp.payload.response;

import lombok.*;
import org.example.quickclothapp.model.Clothe;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponse {
    private UUID uuid;
    private Clothe clothe;
    private Integer stock;
    private Integer minimumStock;
}
