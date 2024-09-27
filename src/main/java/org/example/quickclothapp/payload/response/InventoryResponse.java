package org.example.quickclothapp.payload.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponse {
    private UUID uuid;
    private String clothName;
    private String clothGender;
    private String clothStage;
    private Integer stock;
}
