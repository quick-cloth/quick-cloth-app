package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClotheBankRequest {
    private String name;
    private String address;
    private UUID foundationUuid;
    private UUID cityUuid;
}
