package org.example.quickclothapp.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderList {
    private UUID uuid;
    private Integer value_order;
    private Integer delivery_value;
    private Order order;
    private Clothe clothe;
}
