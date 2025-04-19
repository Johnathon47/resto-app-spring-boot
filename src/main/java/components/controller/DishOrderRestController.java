package components.controller;

import components.model.DishOrder;
import components.service.DishOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishOrderRestController {
    private DishOrderService dishOrderService;

    @Autowired
    public DishOrderRestController(DishOrderService dishOrderService) {
        this.dishOrderService = dishOrderService;
    }

    @GetMapping("/api/dishOrders")
    public List<DishOrder> getAll() {
        return dishOrderService.getAllDishOrder();
    }
}
