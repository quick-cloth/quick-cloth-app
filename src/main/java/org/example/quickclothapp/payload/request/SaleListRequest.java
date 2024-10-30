package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleListRequest {
    private Integer value;
    private UUID clotheUuid;
    private Integer quantity;
}
