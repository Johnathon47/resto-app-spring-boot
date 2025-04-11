package components.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DishOrder {
    private long id;
    private Dish dish;
    private Double quantityToOrder;
    private Double price;
    private Order order;

    @Override
    public String toString() {
        return "DishOrder{" +
                ", name='" + dish.getName() + '\'' +
                ", quantityToOrder=" + quantityToOrder +
                ", order=" + order.getId() +
                '}';
    }
}
