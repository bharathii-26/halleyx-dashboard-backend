package com.example.backend.service;

import com.example.backend.model.Order;
import com.example.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order createOrder(Order order){
        return orderRepository.save(prepareForSave(order, null));
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public Order saveOrder(Order order){
        Order existingOrder = null;

        if (order.getId() != null) {
            Optional<Order> existing = orderRepository.findById(order.getId());
            if (existing.isPresent()) {
                existingOrder = existing.get();
            }
        }

        return orderRepository.save(prepareForSave(order, existingOrder));
    }

    private Order prepareForSave(Order order, Order existingOrder) {
        if (order.getCreatedAt() == null) {
            if (existingOrder != null && existingOrder.getCreatedAt() != null) {
                order.setCreatedAt(existingOrder.getCreatedAt());
            } else {
                order.setCreatedAt(LocalDateTime.now());
            }
        }

        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("Pending");
        }

        if (order.getTotalAmount() <= 0) {
            order.setTotalAmount(order.getQuantity() * order.getUnitPrice());
        }

        return order;
    }
}
