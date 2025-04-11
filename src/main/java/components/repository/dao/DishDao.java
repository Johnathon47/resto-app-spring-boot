package components.repository.dao;

import components.model.Dish;
import components.model.DishIngredient;
import components.model.Ingredient;
import components.model.Unit;
import components.repository.crudOperation.CrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DishDao implements CrudOperation<Dish> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired // Pour que Spring injecte automatiquement la connexion
    public DishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void updateUnitPrice(Dish dish, Connection connection) throws SQLException {
        if (dish.getIngredientList().isEmpty()) {
            System.out.println("La liste des ingredients est vide"); ;
        }
        String updateQuery =
                """
                    UPDATE dish SET unit_price = ? WHERE id = ?;
                """;

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            BigDecimal calculatedPrice = dish.getIngredientCost();
            updateStatement.setBigDecimal(1, calculatedPrice);
            updateStatement.setLong(2, dish.getId());
            updateStatement.executeUpdate();
        }
    }

    @Override
    public List<Dish> getAll(int offset, int limit) {
        List<Dish> dishes = new ArrayList<>();
        Map<Long, Dish> dishMap = new HashMap<>();

        String sql =
                """
                    SELECT dish.id, dish.name AS named, dish_ingredient.required_quantity, dish_ingredient.unit,
                    ingredient.name AS ingredientName, dish.unit_price, ingredient.unit_price AS up
                    FROM dish
                    INNER JOIN dish_ingredient ON dish.id = dish_ingredient.id_dish
                    INNER JOIN ingredient ON dish_ingredient.id_ingredient = ingredient.id
                    ORDER BY dish.id LIMIT ? OFFSET ?;
                """;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long dishId = resultSet.getLong("id");
                    Dish dish = dishMap.get(dishId);
                    if (dish == null) {
                        dish = new Dish();
                        dish.setId(dishId);
                        dish.setName(resultSet.getString("named"));
                        dish.setIngredientList(new ArrayList<>());
                        dishMap.put(dishId, dish);
                        dishes.add(dish);
                    }
                    // Ajouter l'ingrédient au plat
                    String ingredientName = resultSet.getString("ingredientName");
                    BigDecimal requiredQuantity = resultSet.getBigDecimal("required_quantity");
                    String unit = resultSet.getString("unit");
                    BigDecimal unitPrice = resultSet.getBigDecimal("up");

                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientName);
                    ingredient.setUnitPrice(unitPrice);  // Set the unit price from database
                    ingredient.setUnit(Unit.valueOf(unit));

                    DishIngredient dishIngredient = new DishIngredient(ingredient, requiredQuantity, Unit.valueOf(unit));
                    dish.getIngredientList().add(dishIngredient);
                }

                // mettre à jour l'unit price
                for (Dish dish : dishes) {
                    updateUnitPrice(dish, connection);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching dishes", e);
        }

        return dishes;
    }

    @Override
    public void insert(Dish toAddE) {

    }

    @Override
    public void update(Long id, Dish updateE) {

    }

    @Override
    public Dish getById(Long id) {
        return null;
    }
}
