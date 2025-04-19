package components.model;

import java.math.BigDecimal;
import java.util.List;

public class Dish {
    private long id;
    private String name;
    private List<DishIngredient> ingredientList;

    public Dish(long id, String name, List<DishIngredient> listIngredient) {
        this.id = id;
        this.name = name;
        this.ingredientList = listIngredient;
    }

    ;

    public Dish() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIngredientCost() {
        return ingredientList.stream()
                .map(dishIngredient -> dishIngredient.getIngredient()
                        .getCurrentPrice() // 🔁 on utilise cette méthode ici
                        .multiply(dishIngredient.getRequiredQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public List<DishIngredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<DishIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}