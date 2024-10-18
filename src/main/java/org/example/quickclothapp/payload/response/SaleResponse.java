package org.example.quickclothapp.payload.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleResponse {
    private Double totalValue;
    private List<SaleListResponse> saleList;
    private Double payPointsValue;
    private int newPoints;
    private int points;
}
