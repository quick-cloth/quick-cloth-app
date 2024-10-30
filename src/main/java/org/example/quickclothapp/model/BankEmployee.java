package org.example.quickclothapp.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BankEmployee {
    private UUID uuid;
    private ClotheBank clotheBank;
    private User user;
}
