package org.example.quickclothapp.model;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private UUID uuid;
    private String name;
    private String last_name;
    private String user_name;
    private String email;
    private BigInteger document;
    private BigInteger phone;
    private Integer points;
    private LocalDate creation_date;
    private Role role;
    private TypeDocument type_document;
    private String password;
}
