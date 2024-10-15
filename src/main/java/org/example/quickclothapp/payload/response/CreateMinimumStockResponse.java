package org.example.quickclothapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.quickclothapp.model.Clothe;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateMinimumStockResponse {
    private Integer stock;
    private Integer minimumStock;
    private UUID wardrobeUuid;
    private Clothe clothe;
}
