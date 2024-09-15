package org.example.quickclothapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Clothe {
    private UUID uuid;
    private TypeClothe typeClothe;
    private TypeGender typeGender;
    private TypeStage typeStage;
}
