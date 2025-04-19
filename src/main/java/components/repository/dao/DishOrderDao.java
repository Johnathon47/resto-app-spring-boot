package components.repository.dao;

import components.model.DishOrder;
import components.model.Order;
import components.model.OrderDishStatus;
import components.model.dto.DishDTO;
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
public class DishOrderDao implements CrudOperation<DishOrder> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DishOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<DishOrder> getAll(int offset, int limit) {
        String query = "SELECT dish_order.id, dish.name AS name, quantitytoorder, price, orderid, status FROM dish_order INNER JOIN dish ON dish_order.dish_id = dish.id GROUP BY dish_order.id, dish.name LIMIT ? OFFSET ?";
        List<DishOrder> dishOrders = new ArrayList<>();

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DishDTO dishDTO = new DishDTO();
                dishDTO.setName(resultSet.getString("name"));

                Order order = new Order();
                order.setId(resultSet.getLong("orderid"));

                DishOrder dishOrder = new DishOrder(
                        resultSet.getLong("id"),
                        dishDTO,
                        resultSet.getDouble("quantitytoorder"),
                        resultSet.getDouble("price"),
                        order,
                        OrderDishStatus.valueOf(resultSet.getString("status"))
                );
                dishOrders.add(dishOrder);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes de plats", e);
        }

        return dishOrders;
    }

    @Override
    public void insert(DishOrder toAddE) {

    }

    @Override
    public void update(Long id, DishOrder updateE) {

    }

    @Override
    public DishOrder getById(Long id) {
        return null;
    }
}
