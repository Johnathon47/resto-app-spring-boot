package components.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class IngredientPrice {
    private Long id;
    private BigDecimal price;
    private Timestamp updateDateTime;
    private Unit unit;
    private Ingredient ingredient;

    public IngredientPrice() {
    }

    public IngredientPrice(BigDecimal price) {
        this.price = price;
    }

    public IngredientPrice(Long id, BigDecimal price, Unit unit, Timestamp updateDateTime) {
        this.id = id;
        this.price = price;
        this.updateDateTime = updateDateTime;
        this.unit = unit;
    }

    public IngredientPrice(Long id, BigDecimal price, Timestamp updateDateTime, Unit unit, Ingredient ingredient) {
        this.id = id;
        this.price = price;
        this.updateDateTime = updateDateTime;
        this.unit = unit;
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Timestamp updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
