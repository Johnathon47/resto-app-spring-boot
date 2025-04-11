package components.repository.dao;

import components.model.Ingredient;
import components.model.MovementType;
import components.model.StockMovement;
import components.model.Unit;
import components.repository.crudOperation.CrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientDao implements CrudOperation<Ingredient> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IngredientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> getAll(int offset, int limit) {
        List<Ingredient> ingredients = new ArrayList<>();
        String request =
                """
                SELECT id, name, unit_price, unit, update_datetime FROM ingredient OFFSET ? LIMIT ?;
                """;
        try(Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(request);) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("unit_price"),
                        Unit.valueOf(resultSet.getString("unit")),
                        resultSet.getTimestamp("update_datetime")
                );
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public void insert(Ingredient toAddIngredient) {
    String query =
                """
                    INSERT INTO ingredient (id, name, unit_price, unit, update_datetime)
                    VALUES (?, ?, ?, ?::unit, ?);
                """;
    try (PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(query)) {
        preparedStatement.setLong(1, toAddIngredient.getId());
        preparedStatement.setString(2, toAddIngredient.getName());
        preparedStatement.setBigDecimal(3, toAddIngredient.getUnitPrice());
        preparedStatement.setString(4, String.valueOf(toAddIngredient.getUnit()));
        preparedStatement.setTimestamp(5, toAddIngredient.getUpdateDateTime());

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public void update(Long id, Ingredient updateIngredient) {
    String query =
                """
                    UPDATE ingredient SET name = ?, unit_price = ?, unit = ?::unit, update_datetime = ? WHERE id = ?;
                """;
    try (PreparedStatement preparedStatement = jdbcTemplate.getDataSource().getConnection().prepareStatement(query)) {
        preparedStatement.setString(1, updateIngredient.getName());
        preparedStatement.setBigDecimal(2, updateIngredient.getUnitPrice());
        preparedStatement.setString(3, String.valueOf(updateIngredient.getUnit()));
        preparedStatement.setTimestamp(4, updateIngredient.getUpdateDateTime());
        preparedStatement.setLong(5, id);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public Ingredient getById(Long id) {
        String sql =
                """
                SELECT i.id AS ingredient_id, i.name AS ingredient_name, i.unit_price AS ingredient_price,
                       i.unit AS ingredient_unit, i.update_datetime AS ingredient_updated,
                       sm.id AS sm_id, sm.movement_type, sm.quantity, sm.unit AS sm_unit, sm.movement_date
                FROM ingredient i
                LEFT JOIN stock_movement sm ON i.id = sm.ingredient_id
                WHERE i.id = ?;
                """;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Ingredient ingredient = null;
                List<StockMovement> movements = new ArrayList<>();

                while (resultSet.next()) {
                    if (ingredient == null) {
                        ingredient = new Ingredient();
                        ingredient.setId(resultSet.getLong("ingredient_id"));
                        ingredient.setName(resultSet.getString("ingredient_name"));
                        ingredient.setUnitPrice(resultSet.getBigDecimal("ingredient_price"));
                        ingredient.setUnit(Unit.valueOf(resultSet.getString("ingredient_unit")));
                        ingredient.setUpdateDateTime(resultSet.getTimestamp("ingredient_updated"));
                        ingredient.setStockMovementList(movements);
                    }

                    Long smId = resultSet.getLong("sm_id");
                    if (smId != 0) { // s'il y a bien un mouvement de stock lié
                        StockMovement stockMovement = new StockMovement();
                        stockMovement.setId(smId);
                        stockMovement.setMovementType(MovementType.valueOf(resultSet.getString("movement_type")));
                        stockMovement.setQuantity(resultSet.getBigDecimal("quantity"));
                        stockMovement.setUnit(Unit.valueOf(resultSet.getString("sm_unit")));
                        stockMovement.setMovementDate(resultSet.getTimestamp("movement_date"));
                        // Ne pas remettre l’ingredient pour éviter la boucle !
                        stockMovement.setIngredient(null);
                        stockMovement.setIngredientName(resultSet.getString("ingredient_name"));

                        movements.add(stockMovement);
                    }
                }

                return ingredient; // peut être null si non trouvé
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
