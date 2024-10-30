package org.example.quickclothapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TopSellingClothes {
    private String name;
    private String gender;
    private String stage;
    private Integer quantity;
    private Double value;
}
