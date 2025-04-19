package components.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import components.model.dto.DishDTO;

import java.util.Objects;

public class DishOrder {
    private Long id;
    private DishDTO dishDTO;
    private Double quantityToOrder;
    private Double price;
    private Order order;

    public DishOrder(Long id, DishDTO dishDTO, Double quantityToOrder,  Double price, Order order) {
        this.id = id;
        this.dishDTO = dishDTO;
        this.quantityToOrder = quantityToOrder;
        this.price = price;
        this.order = order;
    }

    public DishOrder() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DishDTO getDishDTO() {
        return dishDTO;
    }

    public void setDish(DishDTO dishDTO) {
        this.dishDTO = dishDTO;
    }

    public Double getQuantityToOrder() {
        return quantityToOrder;
    }

    public void setQuantityToOrder(Double quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
