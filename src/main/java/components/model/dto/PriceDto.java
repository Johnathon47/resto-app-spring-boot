package components.model.dto;

import components.model.Unit;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PriceDto {
    private Long id;
    private BigDecimal price;
    private Unit unit;
    private Timestamp updateDateTime;

    public PriceDto(BigDecimal price) {
        this.price = price;
    }

    public PriceDto(Long id, BigDecimal price, Unit unit, Timestamp updateDateTime) {
        this.id = id;
        this.price = price;
        this.unit = unit;
        this.updateDateTime = updateDateTime;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Unit getUnit() {
        return unit;
    }

    public Timestamp getUpdateDateTime() {
        return updateDateTime;
    }
}