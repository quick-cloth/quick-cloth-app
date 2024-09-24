package org.example.quickclothapp.payload.response;

import lombok.*;

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
    private String city;
    private Integer unitSold;
}
