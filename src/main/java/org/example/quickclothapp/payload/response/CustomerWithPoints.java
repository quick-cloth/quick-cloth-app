package org.example.quickclothapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWithPoints {
    private UUID uuid;
    private String name;
    private String lastName;
    private String userName;
    private String email;
    private BigInteger document;
    private BigInteger phone;
    private Integer points;
}
