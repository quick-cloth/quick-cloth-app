package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WardRobeRequest {
    private UUID uuid;
    private String name;
    private String address;
    private UUID clotheBankUuid;
    private UUID cityUuid;
}
