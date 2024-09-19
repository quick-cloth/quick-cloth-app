package org.example.quickclothapp.payload.request;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleRequest {
    private BigInteger value;
    private UUID wardRopeUuid;
    private UUID userUuid;
    private List<SaleListRequest> saleList;
}
