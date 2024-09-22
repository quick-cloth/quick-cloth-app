package org.example.quickclothapp.payload.response;

import lombok.*;
import org.example.quickclothapp.model.ContactUser;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FoundationResponse {
    private UUID uuid;
    private String name;
    private Integer nit;
    private BigInteger phone;
    private String legalRepresentative;
    private String email;
    private LocalDate creationDate;
    private String typeMeetUsName;
    private ContactUser contactUser;
}
