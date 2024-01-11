package com.questionpro.grocery.repository;

import com.questionpro.grocery.request.OrderRequest;
import com.questionpro.grocery.response.GroceryCostResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderedId;
    private double totalAmount;
    private String userId;
    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    public static Orders create(List<OrderDetails> orderDetails, OrderRequest orderRequest, List<GroceryCostResponse> groceryCostList) {
        return Orders.builder().userId(orderRequest.getUserId())
                .totalAmount(groceryCostList.stream().mapToDouble(GroceryCostResponse::getTotalPrice)
                        .sum())
                .orderedId("ORDER"+generateRandomNumber()).
                orderDetails(orderDetails).build();
    }
    public static String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        return String.valueOf(randomNumber);
    }
}
