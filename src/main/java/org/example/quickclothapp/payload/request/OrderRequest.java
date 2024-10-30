package org.example.quickclothapp.payload.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    private UUID wardropeUuid;
    private List <ClotheRequest> clothes;
}
