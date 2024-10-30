package org.example.quickclothapp.payload.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private UUID uuid;
    private String wardrobeName;
    private LocalDate orderDate;
    private Integer quantity;
    private String status;
}
