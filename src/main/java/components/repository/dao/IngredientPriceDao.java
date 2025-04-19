package components.repository.dao;

import components.model.Ingredient;
import components.model.IngredientPrice;
import components.model.Unit;
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
public class IngredientPriceDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IngredientPriceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(IngredientPrice price) {
        String sql = "INSERT INTO ingredient_price (unit_price, unit, update_datetime, ingredient_id) " +
                "VALUES (?, ?::unit, ?, ?)";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBigDecimal(1, price.getPrice());
            ps.setString(2, price.getUnit().name());
            ps.setTimestamp(3, price.getUpdateDateTime());
            ps.setLong(4, price.getIngredient().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<IngredientPrice> findAllForIngredient(long ingredientId) {
        String sql = "SELECT ingredient_price.id AS id_price, unit_price, unit, update_datetime, ingredient.name, ingredient_id FROM ingredient_price " +
                "INNER JOIN ingredient ON ingredient_price.ingredient_id = ingredient.id WHERE ingredient_id = ? ORDER BY update_datetime DESC";
        List<IngredientPrice> prices = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, ingredientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getLong("ingredient_id"));
                ingredient.setName(rs.getString("name"));

                IngredientPrice price = new IngredientPrice();
                price.setId(rs.getLong("id_price"));
                price.setPrice(rs.getBigDecimal("unit_price"));
                price.setUpdateDateTime(rs.getTimestamp("update_datetime"));
                price.setIngredient(ingredient);
                price.setUnit(Unit.valueOf(rs.getString("unit")));

                // facultatif : setIngredient ici si tu veux
                prices.add(price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return prices;
    }
}
