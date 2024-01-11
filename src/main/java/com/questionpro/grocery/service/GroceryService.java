package com.questionpro.grocery.service;

import com.questionpro.grocery.exceptions.EntityNotFoundException;
import com.questionpro.grocery.repository.*;
import com.questionpro.grocery.request.GroceryRequest;
import com.questionpro.grocery.request.OrderItem;
import com.questionpro.grocery.request.OrderRequest;
import com.questionpro.grocery.response.CurrentProduct;
import com.questionpro.grocery.response.GroceryCostResponse;
import com.questionpro.grocery.response.OrderResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GroceryService {

    private final GroceryRepository groceryRepository;
    private final OrderRepository orderRepository;

    public GroceryService(GroceryRepository groceryRepository, OrderRepository orderRepository) {
        this.groceryRepository = groceryRepository;
        this.orderRepository = orderRepository;
    }

    public Grocery createProduct(GroceryRequest groceryRequest) {
        Optional<Grocery> grocery = fetchGroceryProductByName(groceryRequest.getName());
        return grocery.map(value -> updateGroceryProduct(value, groceryRequest))
                .orElseGet(() -> groceryRepository.save(Grocery.createOrUpdate(new Grocery(),groceryRequest)));
    }

    public Void deleteProduct(int productId) {
        fetchGroceryProductByID(productId);
        groceryRepository.deleteById(productId);
        return null;
    }

    public Grocery alterProduct(int productId, GroceryRequest groceryRequest) {
        return groceryRepository.save(Grocery.createOrUpdate(fetchGroceryProductByID(productId),groceryRequest));
    }

    public List<CurrentProduct> fetchProducts() {
        return CurrentProduct.buildResponse(getGroceries());
    }

    public List<CurrentProduct> fetchAvailableProducts() {
        return CurrentProduct.buildResponse(getGroceries().stream()
                .filter(grocery -> grocery.getQuantity()>0).toList());
    }

    private List<Grocery> getGroceries() {
        List<Grocery> groceries = groceryRepository.findAll();
        if(groceries.isEmpty()){
            throw new EntityNotFoundException("Products Not founds");
        }
        return groceries;
    }

    private Grocery updateGroceryProduct(Grocery grocery, GroceryRequest groceryRequest) {
        return groceryRepository.save(Grocery.createOrUpdate(grocery,groceryRequest));
    }

    private Optional<Grocery> fetchGroceryProductByName(String name) {
        return groceryRepository.findByName(name);
    }

    private Grocery fetchGroceryProductByID(int productId) {
        return groceryRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product Not Found"));
    }

    @Transactional
    public OrderResponse orderProducts(OrderRequest orderRequest) {
        double totalAmount=0.0;
        List<GroceryCostResponse> groceryCostList = orderRequest.getOrderItems()
                .stream().map(orderItem -> {
            Grocery grocery = getProductToOrder(orderItem);
            return placeOrder(orderItem, grocery);
        }).toList();
        return OrderResponse.create(orderRequest.getOrderItems(),
                saveOrderDetails(orderRequest,groceryCostList));
    }

    private Orders saveOrderDetails(OrderRequest orderRequest, List<GroceryCostResponse> groceryCostList) {
        List<OrderDetails> orderDetails = OrderDetails.create(orderRequest.getOrderItems());
        Orders orders = Orders.create(orderDetails, orderRequest,groceryCostList);
        orderDetails.forEach(orderDetails1 -> orderDetails1.setOrders(orders));
        return orderRepository.save(orders);
    }

    private GroceryCostResponse placeOrder(OrderItem orderItem, Grocery grocery) {
        if(isProductAvailable(orderItem, grocery)){
            OrderItem confirmedOrder = updateOrder(orderItem, grocery);
            return GroceryCostResponse.create(confirmedOrder,grocery);
        }
        throw new EntityNotFoundException(
                "Groceries are out of the stock ");
    }

    private Grocery getProductToOrder(OrderItem orderItem) {
        return fetchGroceryProductByName(orderItem.getName()).orElseThrow(() -> new EntityNotFoundException(
                "Groceries are not available"));
    }

    private OrderItem updateOrder(OrderItem orderItem, Grocery grocery) {
        grocery.setQuantity(grocery.getQuantity()- orderItem.getQuantity());
        groceryRepository.save(grocery);
        return orderItem;
    }

    private static boolean isProductAvailable(OrderItem orderItem, Grocery grocery) {
        return grocery != null && grocery.getQuantity() >=orderItem.getQuantity();
    }
}
