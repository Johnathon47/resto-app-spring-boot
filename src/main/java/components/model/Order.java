package components.model;

import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {
    private long id;
    private TableNumber tableNumber;
    private Double amountPaid;
    private Double amountDue;
    private Instant customerArrivalDatetime;
    private List<DishOrder> dishOrders;

    public Order(Instant customerArrivalDatetime, Double amountDue, Double amountPaid, TableNumber tableNumber, long id) {
        this.customerArrivalDatetime = customerArrivalDatetime;
        this.amountDue = amountDue;
        this.amountPaid = amountPaid;
        this.tableNumber = tableNumber;
        this.id = id;
        this.dishOrders = new ArrayList<>();  // Initialise la liste des DishOrder
    }
}
