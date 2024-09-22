package org.example.quickclothapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Inventory {
    private UUID uuid;
    private Integer stock;
    private Integer minimum_stock;
    private Wardrobe wardrobe;
    private Clothe clothe;
    private BigInteger unit_price;
}
