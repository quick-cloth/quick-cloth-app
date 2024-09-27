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
public class OrderResponseWardRobe {
    private UUID uuid;
    private LocalDate orderDate;
    private String orderState;
    private Integer orderValue;
    private Integer deliveryValue;
    private List<OrderListResponse> orderList;
}
