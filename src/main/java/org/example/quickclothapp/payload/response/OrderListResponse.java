package org.example.quickclothapp.payload.response;

import lombok.*;
import org.example.quickclothapp.model.Clothe;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderListResponse {
    private Clothe clothe;
    private Integer orderValue;
    private Integer deliveryValue;
}
