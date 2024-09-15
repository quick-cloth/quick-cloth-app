package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.User;
import org.example.quickclothapp.model.Wardrope;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WardropeEmployeeRequest {
    private UUID uuid;
    private Wardrope wardrope;
    private User user;
}