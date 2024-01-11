package com.questionpro.grocery.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroceryRequest {
    @NotBlank(message = "Please provide product name")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Please provide valid product name in string format")
    private String name;
    @NotNull(message = "Please provide product price")
    private Double price;
    @NotNull(message = "Please provide product quantity")
    @Min(value = 1,message = "Please provide least 1 quantity to add product")
    private Integer quantity;
}
