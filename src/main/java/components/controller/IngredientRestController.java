package components.controller;

import components.model.Ingredient;
import components.model.dto.IngredientAvailabilityResponse;
import components.model.dto.IngredientDto;
import components.model.dto.PriceDto;
import components.model.dto.StockMovementResponse;
import components.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class IngredientRestController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredient();

        List<IngredientDto> dtos = ingredients.stream().map(ingredient -> {
            PriceDto latest = new PriceDto(
                    ingredient.getLatestPrice().getId(),
                    ingredient.getLatestPrice().getPrice(),
                    ingredient.getLatestPrice().getUnit(),
                    ingredient.getLatestPrice().getUpdateDateTime()
            );

            List<PriceDto> history = ingredient.getPriceHistory().stream().map(ph -> new PriceDto(
                    ph.getId(),
                    ph.getPrice(),
                    ph.getUnit(),
                    ph.getUpdateDateTime()
            )).toList();

            // ✨ On map les mouvements de stock en StockMovementResponse
            List<StockMovementResponse> stockList = ingredient.getStockMovementList() != null
                    ? ingredient.getStockMovementList().stream()
                    .map(StockMovementResponse::new)
                    .toList()
                    : List.of();


            return new IngredientDto(
                    ingredient.getId(),
                    ingredient.getName(),
                    ingredient.getCurrentPrice(),
                    latest,
                    history,
                    stockList // 👈 Ajout ici
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/ingredients/price")
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

    @PostMapping("/create-ingredient")
    public void insert(@RequestBody Ingredient toAddIngredient) {
        ingredientService.insert(toAddIngredient);
    }

    @PutMapping("/update-ingredient/{id}")
    public void update(@PathVariable Long id, @RequestBody Ingredient updateIngredient) {
        ingredientService.update(id, updateIngredient);
    }

    @GetMapping("/ingredient/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        if (ingredient == null ) {
             return ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .body("Ingredient= id:<"+ id +"> is not found");
        }

        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/ingredient/{id}/availability")
    public ResponseEntity<?> getAvailability(
            @PathVariable Long id,
            @RequestParam(name = "at", required = false) String atDateTimeString) {

        try {
            Ingredient ingredient = ingredientService.getById(id);
            if (ingredient == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingrédient avec l'id " + id + " introuvable.");
            }

            BigDecimal quantity;
            LocalDateTime requestedDateTime;

            if (atDateTimeString == null) {
                requestedDateTime = LocalDateTime.now();
                quantity = ingredientService.getAvailabilityToday(id);
            } else {
                requestedDateTime = LocalDateTime.parse(atDateTimeString);
                quantity = ingredientService.getAvailability(id, Timestamp.valueOf(requestedDateTime));
            }

            IngredientAvailabilityResponse response = new IngredientAvailabilityResponse(
                    ingredient.getId(),
                    ingredient.getName(),
                    quantity,
                    requestedDateTime
            );

            return ResponseEntity.ok(response);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Format de date invalide. Utilisez le format ISO : yyyy-MM-ddTHH:mm:ss");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du calcul de la disponibilité.");
        }
    }


}
