package org.example.quickclothapp.payload.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleWardRobeResponse {
    private UUID uuid;
    private LocalDate date;
    private Integer quantity;
    private String price;
    private String payPoints;
    private List<SaleListWardRobeResponse> saleList;
}
