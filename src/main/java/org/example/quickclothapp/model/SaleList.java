package org.example.quickclothapp.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleList {
    private UUID uuid;
    private Integer value;
    private Sale sale;
    private Clothe clothe;
}
