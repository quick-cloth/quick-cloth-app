package org.example.quickclothapp.payload.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CityResponse {
    private String name;
    private UUID departmentUuid;
}
