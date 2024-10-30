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
public class Foundation {
    private UUID uuid;
    private String name;
    private Integer nit;
    private String legal_representative;
    private BigInteger phone;
    private String email;
    private LocalDate creation_date;
    private TypeMeetUs typeMeetUs;
    private ContactUser contactUser;
    private ClotheBank clotheBank;
    private City city;
}
