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
    private Integer totalStock;
    private String clotheName;
    private List<String> typeGenderList;
    private List<TypeStageResponse> typeStageResponses;
}
