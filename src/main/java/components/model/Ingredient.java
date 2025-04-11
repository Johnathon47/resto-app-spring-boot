package components.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Ingredient {
    private long id;
    private String name;
    private BigDecimal unitPrice;
    private Unit unit;
    private Timestamp updateDateTime;
    List<StockMovement> stockMovementList;

    public Ingredient(long id, String name, BigDecimal unitPrice, Unit unit, Timestamp updateDateTime) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unit = unit;
        this.updateDateTime = updateDateTime;
    }

    public Ingredient (String name) {
        this.name = name;
    }

    public Ingredient () {}

    @Override
    public String toString() {
        return "Ingredient{" +
                "\n  id=" + id +
                ",\n name='" + name + '\'' +
                ",\n unitPrice=" + unitPrice +
                ",\n unit=" + unit +
                ",\n updateDateTime=" + updateDateTime +
                '}'+"\n";
    }
}
