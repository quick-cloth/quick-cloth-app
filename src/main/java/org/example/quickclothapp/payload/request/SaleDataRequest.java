package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.Sale;
import org.example.quickclothapp.model.SaleList;


import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleDataRequest {
    Sale sale;
    List<SaleList> saleListRequests;
}