package org.example.quickclothapp.payload.response;

import lombok.*;
import org.example.quickclothapp.model.City;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WardRobeResponse {
    private UUID uuid;
    private String name;
    private String address;
    private Integer stock;
    private String valueSales;
    private City city;
    private Integer unitSold;
}
