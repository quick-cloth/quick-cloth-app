package org.example.quickclothapp.payload.response;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
    private String message;
    private Integer value;
    private UUID uuid;
}
