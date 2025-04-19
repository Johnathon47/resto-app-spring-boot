package components.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class IngredientDto {
    private Long id;
    private String name;
    private BigDecimal currentPrice;
    private PriceDto latestPrice;
    private List<PriceDto> priceHistory;
    private List<StockMovementResponse> stockMovements; // 👉 ajout ici

    public IngredientDto(
            Long id,
            String name,
            BigDecimal currentPrice,
            PriceDto latestPrice,
            List<PriceDto> priceHistory,
            List<StockMovementResponse> stockMovements // 👉 ajout ici
    ) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.latestPrice = latestPrice;
        this.priceHistory = priceHistory;
        this.stockMovements = stockMovements; // 👉 ajout ici
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public PriceDto getLatestPrice() {
        return latestPrice;
    }

    public List<PriceDto> getPriceHistory() {
        return priceHistory;
    }

    public List<StockMovementResponse> getStockMovements() {
        return stockMovements; // 👉 getter pour le frontend
    }
}
