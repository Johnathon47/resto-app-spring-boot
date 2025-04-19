package components.controller;

import components.model.Dish;
import components.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishRestController {
    private DishService dishService;

    @Autowired
    public DishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public List<Dish> getAllDish(){
        return dishService.getAllDish();
    }
}
