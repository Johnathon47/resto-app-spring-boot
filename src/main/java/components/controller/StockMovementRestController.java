package components.controller;

import components.model.StockMovement;
import components.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockMovementRestController {
    private final StockMovementService movementService;

    @Autowired
    public StockMovementRestController(StockMovementService stockMovementService) {
        this.movementService = stockMovementService;
    }

    @GetMapping("/stockmovementlist")
    public List<StockMovement> movementList() {
        return movementService.getAll();
    }
}
