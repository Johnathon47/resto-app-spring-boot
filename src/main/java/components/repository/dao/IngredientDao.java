package components.repository.dao;

import components.model.Ingredient;
import components.repository.crudOperation.CrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientDao implements CrudOperation<Ingredient> {
    private final JdbcTemplate jdbcTemplate;
    private final IngredientPriceDao priceDao;

    @Autowired
    public IngredientDao(JdbcTemplate jdbcTemplate, IngredientPriceDao priceDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.priceDao = priceDao;
    }

    @Override
    public List<Ingredient> getAll(int offset, int limit) {
        String sql = """
            SELECT id, name
            FROM ingredient
            ORDER BY name
            LIMIT ? OFFSET ?;
        """;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            List<Ingredient> ingredients = new ArrayList<>();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                long id = rs.getLong("id");
                ingredient.setId(id);
                ingredient.setName(rs.getString("name"));

                // Charger les prix depuis ingredient_price
                ingredient.setPriceHistory(priceDao.findAllForIngredient(id));

                ingredients.add(ingredient);
            }

            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ingrédients", e);
        }
    }

    @Override
    public void insert(Ingredient toAddIngredient) {
        String sql = "INSERT INTO ingredient (id, name) VALUES (?, ?)";

        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            ps.setLong(1, toAddIngredient.getId());
            ps.setString(2, toAddIngredient.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'ingrédient", e);
        }
    }

    @Override
    public void update(Long id, Ingredient updateIngredient) {
        String sql = "UPDATE ingredient SET name = ? WHERE id = ?";

        try (PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(sql)) {
            ps.setString(1, updateIngredient.getName());
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'ingrédient", e);
        }
    }

    @Override
    public Ingredient getById(Long id) {
        String sql = "SELECT id, name FROM ingredient WHERE id = ?";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getLong("id"));
                ingredient.setName(rs.getString("name"));

                // Charger les prix depuis ingredient_price
                ingredient.setPriceHistory(priceDao.findAllForIngredient(id));

                return ingredient;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ingrédient par ID", e);
        }
    }
}
