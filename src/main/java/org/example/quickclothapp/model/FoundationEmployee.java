package org.example.quickclothapp.model;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoundationEmployee {
    private UUID uuid;
    private Foundation foundation;
    private User user;
}
