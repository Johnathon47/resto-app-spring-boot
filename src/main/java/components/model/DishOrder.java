package components.model;


import components.model.dto.DishDTO;


public class DishOrder {
    private Long id;
    private DishDTO dishDTO;
    private Double quantityToOrder;
    private Double price;
    private Order order;
    private OrderDishStatus orderDishStatus;

    public DishOrder(Long id, DishDTO dishDTO, Double quantityToOrder,  Double price, Order order, OrderDishStatus orderDishStatus) {
        this.id = id;
        this.dishDTO = dishDTO;
        this.quantityToOrder = quantityToOrder;
        this.price = price;
        this.order = order;
        this.orderDishStatus = orderDishStatus;
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

    public void setDishDTO(DishDTO dishDTO) {
        this.dishDTO = dishDTO;
    }

    public OrderDishStatus getOrderDishStatus() {
        return orderDishStatus;
    }

    public void setOrderDishStatus(OrderDishStatus orderDishStatus) {
        this.orderDishStatus = orderDishStatus;
    }
}
