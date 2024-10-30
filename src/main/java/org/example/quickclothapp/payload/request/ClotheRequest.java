package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClotheRequest {
    private UUID clotheUuid;
    private Integer quantity;
}
