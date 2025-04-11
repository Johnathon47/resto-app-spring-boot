package components.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StockMovement {
    private long id;
    @JsonIgnore
    private Ingredient ingredient;
    private String ingredientName;
    private MovementType movementType;
    private BigDecimal quantity;
    private Unit unit;
    private Timestamp movementDate;
}
