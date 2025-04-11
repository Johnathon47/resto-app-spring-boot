package components.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DishIngredient extends Ingredient{
    private Ingredient ingredient;
    private BigDecimal requiredQuantity;
    private Unit unit;

    public DishIngredient() {}

    public DishIngredient(Ingredient ingredient, BigDecimal requiredQuality, Unit unit) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuality;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "DishIngredient{" +
                "name=" + ingredient.getName() +
                ", requiredQuantity=" + requiredQuantity +
                ", unit=" + unit + '\'' +
                '}';
    }
}
