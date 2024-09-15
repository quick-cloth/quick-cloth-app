package org.example.quickclothapp.model;


import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactUser {
    private UUID uuid;
    private String name;
    private String last_name;
    private BigInteger phone;
    private String email;
}
