package com.questionpro.grocery.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class OrderItem {
    @NotBlank(message = "Please provide product name")
    private String name;
    @NotNull(message = "Please provide product quantity")
    @Min(value = 1,message = "Please provide least 1 quantity to add product")
    private Integer quantity;
}
