package org.example.quickclothapp.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WardRobeEmployee {
    private UUID uuid;
    private Wardrobe wardrobe;
    private User user;
}
