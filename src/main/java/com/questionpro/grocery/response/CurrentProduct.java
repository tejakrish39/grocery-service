package com.questionpro.grocery.response;

import com.questionpro.grocery.repository.Grocery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentProduct {
    private String name;
    private int quantity;

    public static List<CurrentProduct> buildResponse(List<Grocery> groceries) {
        return groceries.stream().map(grocery -> {
            return CurrentProduct.builder().name(grocery.getName()).quantity(grocery.getQuantity()).build();
        }).toList();
    }
}
