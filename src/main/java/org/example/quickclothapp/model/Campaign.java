package org.example.quickclothapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Campaign {
    private UUID uuid;
    private String name;
    private String message_campaign;
    private LocalDate creation_date;
    private LocalDate end_date;
    private ClotheBank clotheBank;
    private TypeCampaign typeCampaign;
    private TypeClothe typeClothe;
    private TypeGender typeGender;
    private TypeStage typeStage;
    private Integer discount;
}
