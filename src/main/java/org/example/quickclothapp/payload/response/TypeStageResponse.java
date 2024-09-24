package org.example.quickclothapp.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TypeStageResponse {
    private String typeGenderName;
    private String name;
    private Integer stock;
}
