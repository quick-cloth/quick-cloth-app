package org.example.quickclothapp.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleListWardRobeResponse {
    private String clotheName;
    private String typeGenderName;
    private String typeStageName;
    private Integer quantity;
    private String value;
}
