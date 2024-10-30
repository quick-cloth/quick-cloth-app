package org.example.quickclothapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quickclothapp.model.Wardrobe;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesByUserResponse {
    private String uuid;
    private LocalDate saleDate;
    private double value;
    private BigInteger payPoints;
    private Wardrobe wardrobe;
}
