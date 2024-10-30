package org.example.quickclothapp.payload.request;

import lombok.*;
import org.example.quickclothapp.model.Order;
import org.example.quickclothapp.model.OrderList;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDataRequest {
    Order order;
    List<OrderList> orderList;
}
