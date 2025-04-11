package components.controller;

import components.model.Ingredient;
import components.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IngredientController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public String getAllIngredient(Model model) {
        List<Ingredient> ingredients = ingredientService.getAllIngredient();
        model.addAttribute("ingredients", ingredients);
        return "ingredients";
    }
}
