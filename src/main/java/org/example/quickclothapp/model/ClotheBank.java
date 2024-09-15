package org.example.quickclothapp.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClotheBank {
    private UUID uuid;
    private String name;
    private String address;
    private Foundation foundation;
    private City city;
}
