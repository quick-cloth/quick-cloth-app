package org.example.quickclothapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quickclothapp.model.Wardrobe;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private UUID uuid;
    private String name;
    private String lastName;
    private String userName;
    private String email;
    private String points;
}
