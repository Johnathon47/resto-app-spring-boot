package components.service;

import components.model.StockMovement;
import components.repository.dao.StockMovementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementService {
    private final StockMovementDao stockMovementDao;

    @Autowired
    public StockMovementService(StockMovementDao stockMovementDao) {
        this.stockMovementDao = stockMovementDao;
    }

    public List<StockMovement> getAll() {
        return stockMovementDao.stockMovementList();
    }
}
