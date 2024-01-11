package com.questionpro.grocery.response;

import com.questionpro.grocery.repository.Grocery;
import com.questionpro.grocery.request.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroceryCostResponse {
    private String name;
    private int quantity;
    private double totalPrice;

    public static GroceryCostResponse create(OrderItem confirmedOrder, Grocery grocery) {
        return GroceryCostResponse.builder()
                .name(confirmedOrder.getName())
                .totalPrice(grocery.getPrice()*confirmedOrder.getQuantity())
                .quantity(confirmedOrder.getQuantity())
                .build();
    }
}
