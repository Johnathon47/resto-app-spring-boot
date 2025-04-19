package components.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IngredientAvailabilityResponse {
    private Long ingredientId;
    private String ingredientName;
    private BigDecimal availability;
    private LocalDateTime at;

    public IngredientAvailabilityResponse(Long ingredientId, String ingredientName, BigDecimal availability, LocalDateTime at) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.availability = availability;
        this.at = at;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public BigDecimal getAvailability() {
        return availability;
    }

    public LocalDateTime getAt() {
        return at;
    }
}
