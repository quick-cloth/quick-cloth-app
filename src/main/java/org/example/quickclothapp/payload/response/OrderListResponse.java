package org.example.quickclothapp.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderListResponse {
    private String clotheName;
    private String genderName;
    private String stageName;
    private Integer orderValue;
    private Integer deliveryValue;
}
