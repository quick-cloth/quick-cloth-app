package org.example.quickclothapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCreateMinimumStockResponse {
    List<CreateMinimumStockResponse> createMinimumStockResponses;
}
