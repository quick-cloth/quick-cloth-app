package org.example.quickclothapp.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private UUID uuid;
    private LocalDate order_date;
    private LocalDate delivery_date;
    private OrderState orderState;
    private Wardrope wardrope;
}
