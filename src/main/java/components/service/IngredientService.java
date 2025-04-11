package components.service;

import components.model.Ingredient;
import components.repository.dao.IngredientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private IngredientDao ingredientDao;

    @Autowired
    public IngredientService(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    public List<Ingredient> getAllIngredient() {
        return ingredientDao.getAll(0,10);
    }

    public List<Ingredient> getAllByPrice(BigDecimal priceMinFilter, BigDecimal priceMaxFilter) {
        List<Ingredient> ingredients = ingredientDao.getAll(0, 10);
        return ingredients.stream()
                .filter(ingredient ->
                        ingredient.getUnitPrice().compareTo(priceMinFilter) >= 0 &&
                        ingredient.getUnitPrice().compareTo(priceMaxFilter) <=0)
                .collect(Collectors.toList());
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
