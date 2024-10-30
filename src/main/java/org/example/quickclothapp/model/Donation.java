package org.example.quickclothapp.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Donation {
    private UUID uuid;
    private LocalDate creation_date;
    private ClotheBank clothe_bank;
    private User user;
    private Clothe clothe;
    private Integer quantity;
}