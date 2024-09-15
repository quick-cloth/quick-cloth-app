package org.example.quickclothapp.payload.request;

import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequest {
    private String name;
    private String lastName;
    private String userName;
    private String email;
    private BigInteger phone;
    private String documentNumber;
    private UUID typeDocumentUuid;
}
