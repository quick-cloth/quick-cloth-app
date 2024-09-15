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
public class Inventory {
    private UUID uuid;
    private Integer stock;
    private Integer minimum_stock;
    private Wardrope wardrope;
    private Clothe clothe;
}
