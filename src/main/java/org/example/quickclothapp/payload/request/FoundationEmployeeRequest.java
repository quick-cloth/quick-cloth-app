package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.Foundation;
import org.example.quickclothapp.model.User;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FoundationEmployeeRequest {
    private UUID uuid;
    private Foundation foundation;
    private User user;
}
