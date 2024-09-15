package org.example.quickclothapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Sale {
    private UUID uuid;
    private BigInteger value;
    private LocalDate sale_date;
    private Wardrope wardrope;
    private User user;
}
