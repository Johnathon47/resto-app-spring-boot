package components.model;


import java.math.BigDecimal;

public class DishIngredient {
    private Ingredient ingredient;
    private BigDecimal requiredQuantity;
    private Unit unit;

    public DishIngredient() {}

    public DishIngredient(Ingredient ingredient, BigDecimal requiredQuality, Unit unit) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuality;
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public BigDecimal getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}