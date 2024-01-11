package com.questionpro.grocery.repository;

import com.questionpro.grocery.request.GroceryRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Grocery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private int quantity;

    public static Grocery create(Grocery grocery) {
        return Grocery.builder().name(grocery.getName())
                .price(grocery.getPrice())
                .quantity(grocery.getQuantity())
                .build();
    }

    public static Grocery createOrUpdate(Grocery grocery, GroceryRequest groceryRequest) {
        return Grocery.builder()
                .id(grocery.id!=null ? grocery.id : null).name(groceryRequest.getName())
                .price(groceryRequest.getPrice())
                .quantity(groceryRequest.getQuantity())
                .build();
    }
}
