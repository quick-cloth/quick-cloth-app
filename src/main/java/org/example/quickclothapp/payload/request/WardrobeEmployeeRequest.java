package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.User;
import org.example.quickclothapp.model.Wardrobe;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WardrobeEmployeeRequest {
    private UUID uuid;
    private Wardrobe wardrope;
    private User user;
}