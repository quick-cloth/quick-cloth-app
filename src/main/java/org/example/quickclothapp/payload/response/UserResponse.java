package org.example.quickclothapp.payload.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private UUID uuid;
    private String name;
    private String lastName;
    private String email;
}
