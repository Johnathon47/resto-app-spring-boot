package components.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockMovement {
    private Long id;
    private String ingredientName;
    private MovementType movementType;
    private BigDecimal quantity;
    private Unit unit;
    private Timestamp movementDate;

    public StockMovement() {
    }

    public StockMovement(Long id, String ingredientName, MovementType movementType, BigDecimal quantity, Unit unit, Timestamp movementDate) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.movementType = movementType;
        this.quantity = quantity;
        this.unit = unit;
        this.movementDate = movementDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Timestamp getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Timestamp movementDate) {
        this.movementDate = movementDate;
    }

}
