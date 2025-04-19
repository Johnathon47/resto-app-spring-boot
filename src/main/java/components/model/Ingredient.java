package components.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

public class Ingredient {
    private Long id;
    protected String name;
    private List<IngredientPrice> priceHistory;
    private List<StockMovement> stockMovementList;


    public Ingredient(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ingredient (String name) {
        this.name = name;
    }

    public Ingredient() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientPrice> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<IngredientPrice> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public List<StockMovement> getStockMovementList() {
        return stockMovementList;
    }

    public void setStockMovementList(List<StockMovement> stockMovementList) {
        this.stockMovementList = stockMovementList;
    }

    public IngredientPrice getLatestPrice() {
        if (priceHistory == null || priceHistory.isEmpty()) return null;

        return priceHistory.stream()
                .max((p1, p2) -> p1.getUpdateDateTime().compareTo(p2.getUpdateDateTime()))
                .orElse(null);
    }

    public BigDecimal getCurrentPrice() {
        IngredientPrice latest = getLatestPrice();
        return latest != null ? latest.getPrice() : BigDecimal.ZERO;
    }
}
