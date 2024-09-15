package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WardRopeRequest {
    private String name;
    private String address;
    private UUID clotheBankUuid;
    private UUID cityUuid;
}
