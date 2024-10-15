package org.example.quickclothapp.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClotheByAllTypesRequest {
    private UUID typeClotheUuid;
    private UUID typeGenderUuid;
    private UUID typeStageUuid;
}
