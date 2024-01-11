package com.questionpro.grocery.response;

import com.questionpro.grocery.repository.Orders;
import com.questionpro.grocery.request.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderResponse {
    private String orderGreeting;
    private String orderId;
    private double totalAmount;
    private List<OrderItem> orderList;

    public static OrderResponse create(List<OrderItem> orderItemList, Orders orders) {
        return OrderResponse.builder().orderGreeting("Thank you for Ordering")
                .orderId(orders.getOrderedId())
                .totalAmount(orders.getTotalAmount())
                .orderList(orderItemList)
                .build();
    }
}
