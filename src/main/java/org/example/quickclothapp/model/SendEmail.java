package org.example.quickclothapp.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendEmail {
    private UUID uuid;
    private String email;
    private LocalDate send_date;
}