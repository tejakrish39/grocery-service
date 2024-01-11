package com.questionpro.grocery;

import com.questionpro.grocery.repository.Grocery;
import com.questionpro.grocery.request.GroceryRequest;
import com.questionpro.grocery.request.OrderRequest;
import com.questionpro.grocery.response.CurrentProduct;
import com.questionpro.grocery.response.OrderResponse;
import com.questionpro.grocery.service.GroceryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @PostMapping(path = "/store/product")
    @Operation(summary = "Add new grocery item to the store")
    public ResponseEntity<Grocery> addProduct(@Valid @RequestBody GroceryRequest groceryRequest){
        return new ResponseEntity<>(groceryService.createProduct(groceryRequest),HttpStatus.CREATED);
    }

    @GetMapping(path = "/store/products")
    @Operation(summary = "Get all the existing grocery items")
    public ResponseEntity<List<CurrentProduct>> getProducts(){
        return new ResponseEntity<>(groceryService.fetchProducts(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/store/product/{productId}")
    @Operation(summary = "Delete the existing grocery item")
    public ResponseEntity<Void> removeProduct(@PathVariable int productId){
        return new ResponseEntity<>(groceryService.deleteProduct(productId),HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/store/product/{productId}")
    @Operation(summary = "Update the existing grocery item")
    public ResponseEntity<Grocery> updateProduct(@PathVariable int productId, @Valid @RequestBody GroceryRequest groceryRequest){
        return new ResponseEntity<>( groceryService.alterProduct(productId, groceryRequest),HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/super-market/products")
    @Operation(summary = "Get all the available grocery items")
    public ResponseEntity<List<CurrentProduct>> getAvailableProducts(){
        return new ResponseEntity<>(groceryService.fetchAvailableProducts(),HttpStatus.OK);
    }

    @PostMapping(path = "/super-market/order")
    @Operation(summary = "Order grocery items for user")
    public ResponseEntity<OrderResponse> addProduct(@Valid @RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(groceryService.orderProducts(orderRequest),HttpStatus.CREATED);
    }

}
