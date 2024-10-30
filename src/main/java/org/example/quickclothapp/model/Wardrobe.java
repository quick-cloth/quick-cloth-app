package org.example.quickclothapp.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wardrobe {
    private UUID uuid;
    private String name;
    private LocalDate creation_date;
    private String address;
    private ClotheBank clotheBank;
    private City city;
}