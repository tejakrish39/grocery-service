package com.questionpro.grocery.repository;

import com.questionpro.grocery.request.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="orders_id")
    private Orders orders;

    public static List<OrderDetails> create(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(orderItems -> {
            return OrderDetails.builder().name(orderItems.getName())
                    .quantity(orderItems.getQuantity()).build();
        }).toList();
    }
}
