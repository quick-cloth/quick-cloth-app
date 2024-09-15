package org.example.quickclothapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderList {
    private UUID uuid;
    private Integer value_order;
    private Integer delivery_value;
    private Order order;
    private Clothe clothe;
}
