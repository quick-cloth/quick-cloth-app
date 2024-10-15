package org.example.quickclothapp.payload.response;

import lombok.*;
import org.example.quickclothapp.model.Wardrobe;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesByUserWithPointsResponse extends SalesByUserResponse{
    private int earnedPoints;

    public SalesByUserWithPointsResponse(String uuid, LocalDate date, double value, BigInteger payPoints, Wardrobe wardrobe, int i) {
        super(uuid, date, value, payPoints, wardrobe);
        this.earnedPoints = i;
    }
}
