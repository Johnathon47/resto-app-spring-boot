package components.model.dto;

import components.model.StockMovement;
import components.model.Unit;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StockMovementResponse {
    private Long id;
    private String movementType;
    private BigDecimal quantity;
    private Unit unit;
    private Timestamp movementDate;

    public StockMovementResponse(StockMovement movement) {
        this.id = movement.getId();
        this.movementType = movement.getMovementType().name(); // ENTRY ou EXIT
        this.quantity = movement.getQuantity();
        this.unit = movement.getUnit();
        this.movementDate = movement.getMovementDate();
    }

    public Long getId() {
        return id;
    }

    public String getMovementType() {
        return movementType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public Timestamp getMovementDate() {
        return movementDate;
    }
}

