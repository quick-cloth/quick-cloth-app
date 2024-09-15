package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.ClotheBank;
import org.example.quickclothapp.model.User;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankEmployeeRequest {
    private UUID uuid;
    private ClotheBank clotheBank;
    private User user;
}