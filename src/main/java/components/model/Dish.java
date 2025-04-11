package components.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
public class Dish {
    private long id;
    private String name;
    private List<DishIngredient> ingredientList;

    public Dish() {}

    public Dish(long id, String name, List<DishIngredient> listIngredient){
        this.id = id;
        this.name = name;
        this.ingredientList = listIngredient;
    };

    public BigDecimal getIngredientCost() {
        BigDecimal total = BigDecimal.valueOf(0);
        return ingredientList.stream()
                .map(dishIngredient -> dishIngredient.getIngredient()
                        .getUnitPrice()
                        .multiply(dishIngredient.getRequiredQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        /*for (DishIngredient dishIngredient: ingredientList) {
            Ingredient ingredient = dishIngredient.getIngredient();
            BigDecimal ingredientCost = ingredient.getUnitPrice().multiply(dishIngredient.getRequiredQuantity());
            total = total.add(ingredientCost);
        }
        return total;*/
    }

    @Override
    public String toString() {
        return "Dish{" +
                " \n  id=" + id +
                ",\n name='" + name + '\'' +
                ",\n unitPrice=" + getIngredientCost() +
                ",\n ingredientList=" + ingredientList +
                '}';
    }
}
