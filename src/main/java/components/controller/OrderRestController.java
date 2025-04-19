package components.controller;

import components.model.Order;
import components.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestController {
    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/orders")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/api/order/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getById(id);
    }
}
