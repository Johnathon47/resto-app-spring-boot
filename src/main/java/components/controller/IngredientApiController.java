package components.controller;

import components.model.Ingredient;
import components.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class IngredientApiController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientApiController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/api/ingredients")
    public List<Ingredient> getAllIngredient(){
        return ingredientService.getAllIngredient();
    }
    @GetMapping("/api/ingredients/price")
    public ResponseEntity<?> getAllPriceIngredient(
            @RequestParam(name = "priceMin", required = false) BigDecimal priceMin,
            @RequestParam(name = "priceMax", required = false) BigDecimal priceMax) {

        // Vérification des paramètres de filtrage
        if ((priceMin != null && priceMin.compareTo(BigDecimal.ZERO) < 0) && (priceMax != null && priceMax.compareTo(BigDecimal.ZERO) < 0)) {
            return ResponseEntity.badRequest().body("Le priceMaxFilter (" + priceMax + ") ne peut pas être inférieur à 0 et Le priceMinFilter (" + priceMin + ") ne peut pas être inférieur à 0. ");
        }

        if (priceMin != null && priceMin.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().body("Le priceMinFilter (" + priceMin + ") ne peut pas être inférieur à 0.");
        }

        if (priceMax != null && priceMax.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.badRequest().body("Le priceMaxFilter (" + priceMax + ") ne peut pas être inférieur à 0.");
        }

        if (priceMin != null && priceMax != null && priceMax.compareTo(priceMin) < 0) {
            return ResponseEntity.badRequest().body("Le priceMinFilter (" + priceMin + ") ne peut pas être supérieur au priceMaxFilter (" + priceMax + ").");
        }

        // Si toutes les vérifications sont ok, procéder au filtrage des ingrédients
        if (priceMin == null) {
            priceMin = BigDecimal.ZERO;
        }

        if (priceMax == null) {
            priceMax = BigDecimal.valueOf(10000);
        }

        // Appeler le service pour appliquer les filtres
        List<Ingredient> filteredIngredients = ingredientService.getAllByPrice(priceMin, priceMax);

        return ResponseEntity.ok(filteredIngredients);
    }

    @PostMapping("/api/create-ingredient")
    public void insert(@RequestBody Ingredient toAddIngredient) {
        ingredientService.insert(toAddIngredient);
    }

    @PutMapping("/api/update-ingredient/{id}")
    public void update(@PathVariable Long id, @RequestBody Ingredient updateIngredient) {
        ingredientService.update(id, updateIngredient);
    }

    @GetMapping("/api/ingredient/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        if (ingredient == null ) {
             return ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .body("Ingredient= id:<"+ id +"> is not found");
        }

        return ResponseEntity.ok(ingredient);
    }
}
