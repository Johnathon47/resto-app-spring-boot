package components.repository.dao;

import components.model.MovementType;
import components.model.StockMovement;
import components.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StockMovementDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StockMovement> stockMovementList () {
        List<StockMovement> movementList = new ArrayList<>();
        String query =
                """
                    SELECT stock_movement.id, name,stock_movement.movement_type, stock_movement.quantity, stock_movement.unit, stock_movement.movement_date 
                    FROM ingredient INNER JOIN stock_movement ON ingredient.id = stock_movement.ingredient_id;
                """;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement prstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = prstmt.executeQuery();
            while (resultSet.next()) {
                StockMovement stockMovement = new StockMovement();
                stockMovement.setId(resultSet.getLong("id"));
                stockMovement.setIngredientName(resultSet.getString("name"));
                stockMovement.setMovementType(MovementType.valueOf(resultSet.getString("movement_type")));
                stockMovement.setQuantity(resultSet.getBigDecimal("quantity"));
                stockMovement.setUnit(Unit.valueOf(resultSet.getString("unit")));
                stockMovement.setMovementDate(resultSet.getTimestamp("movement_date"));

                movementList.add(stockMovement);
            }
            return movementList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StockMovement> getMovementsByIngredientAndDate(Long ingredientId, Timestamp beforeDate) {
        List<StockMovement> movementList = new ArrayList<>();
        String query = """
        SELECT stock_movement.id, ingredient.name, stock_movement.movement_type, 
               stock_movement.quantity, stock_movement.unit, stock_movement.movement_date 
        FROM stock_movement 
        INNER JOIN ingredient ON ingredient.id = stock_movement.ingredient_id 
        WHERE ingredient.id = ? AND movement_date <= ?
    """;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement prstmt = connection.prepareStatement(query)) {
            prstmt.setLong(1, ingredientId);
            prstmt.setTimestamp(2, beforeDate);

            ResultSet resultSet = prstmt.executeQuery();
            while (resultSet.next()) {
                StockMovement stockMovement = new StockMovement();
                stockMovement.setId(resultSet.getLong("id"));
                stockMovement.setIngredientName(resultSet.getString("name"));
                stockMovement.setMovementType(MovementType.valueOf(resultSet.getString("movement_type")));
                stockMovement.setQuantity(resultSet.getBigDecimal("quantity"));
                stockMovement.setUnit(Unit.valueOf(resultSet.getString("unit")));
                stockMovement.setMovementDate(resultSet.getTimestamp("movement_date"));

                movementList.add(stockMovement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movementList;
    }

}
