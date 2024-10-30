package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.ContactUser;

import java.math.BigInteger;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FoundationRequest {
    private UUID uuid;
    private String name;
    private Integer nit;
    private BigInteger phone;
    private String legalRepresentative;
    private String email;
    private UUID typeMeetUsUuid;
    private ContactUser contactUser;
    private UUID clotheBankUuid;
    private UUID cityUuid;
}
