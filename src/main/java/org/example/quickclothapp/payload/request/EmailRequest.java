package org.example.quickclothapp.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {
    private String to;
    private String subject;
    private String message;
}
