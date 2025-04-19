package components.service;

import components.model.Ingredient;
import components.model.IngredientPrice;
import components.model.MovementType;
import components.model.StockMovement;
import components.repository.dao.IngredientDao;
import components.repository.dao.StockMovementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientDao ingredientDao;
    private final StockMovementDao stockMovementDao;

    @Autowired
    public IngredientService(IngredientDao ingredientDao, StockMovementDao stockMovementDao) {
        this.ingredientDao = ingredientDao;
        this.stockMovementDao = stockMovementDao;
    }

    public List<Ingredient> getAllIngredient() {
        return ingredientDao.getAll(0, 10);
    }

    public List<Ingredient> getAllByPrice(BigDecimal priceMinFilter, BigDecimal priceMaxFilter) {
        List<Ingredient> ingredients = ingredientDao.getAll(0, 10);
        return ingredients.stream()
                .filter(ingredient -> {
                    IngredientPrice latestPrice = getLatestPrice(ingredient);
                    return latestPrice != null &&
                            latestPrice.getPrice().compareTo(priceMinFilter) >= 0 &&
                            latestPrice.getPrice().compareTo(priceMaxFilter) <= 0;
                })
                .collect(Collectors.toList());
    }

    private IngredientPrice getLatestPrice(Ingredient ingredient) {
        return ingredient.getPriceHistory()
                .stream()
                .max(Comparator.comparing(IngredientPrice::getUpdateDateTime))
                .orElse(null);
    }

    public BigDecimal getAvailability(Long ingredientId, Timestamp atDate) {
        List<StockMovement> movements = stockMovementDao.getMovementsByIngredientAndDate(ingredientId, atDate);

        BigDecimal total = BigDecimal.ZERO;
        for (StockMovement movement : movements) {
            if (movement.getMovementType() == MovementType.ENTRY) {
                total = total.add(movement.getQuantity());
            } else if (movement.getMovementType() == MovementType.EXIT) {
                total = total.subtract(movement.getQuantity());
            }
        }

        return total;
    }

    // Version simplifiée pour aujourd’hui
    public BigDecimal getAvailabilityToday(Long ingredientId) {
        return getAvailability(ingredientId, new Timestamp(System.currentTimeMillis()));
    }


    public void insert(Ingredient toAddIngredient) {
        ingredientDao.insert(toAddIngredient);
    }

    public void update(Long id, Ingredient toUpdateIngredient) {
        ingredientDao.update(id, toUpdateIngredient);
    }

    public Ingredient getById(Long id) {
        return ingredientDao.getById(id);
    }
}
