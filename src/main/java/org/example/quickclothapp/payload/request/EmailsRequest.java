package org.example.quickclothapp.payload.request;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailsRequest {
    private String[] to;
    private String subject;
    private String message;
}
