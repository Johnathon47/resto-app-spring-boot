package components.controller;

import components.model.Dish;
import components.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DishController {
    private DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }


    @GetMapping("/dishes")
    public String getAllDishes(Model model) {
        List<Dish> dishes = dishService.getAllDish();
        model.addAttribute("dishes", dishes);
        return "dishes";
    }
}
