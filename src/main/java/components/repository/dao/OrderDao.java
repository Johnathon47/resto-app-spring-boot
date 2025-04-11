package components.repository.dao;

import components.model.Dish;
import components.model.DishOrder;
import components.model.Order;
import components.model.TableNumber;
import components.repository.crudOperation.CrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDao implements CrudOperation<Order> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Order> getAll(int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        String getOrdersQuery = """
        SELECT orderid, "order".tablenumber, dish.name, quantitytoorder, price, 
               "order".amountpaid, "order".amountdue, "order".customerarrivaldatetime
        FROM dish_order
        INNER JOIN dish ON dish_order.dish_id = dish.id
        INNER JOIN "order" ON dish_order.orderid = "order".id
        ORDER BY orderid
        OFFSET ? LIMIT ?
    """;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(getOrdersQuery)) {

            stmt.setInt(1, offset);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();

            Map<Long, Order> orderMap = new HashMap<>();

            while (rs.next()) {
                Long orderId = rs.getLong("orderid");

                // Si l'ordre n'est pas encore dans la liste, on le crée
                if (!orderMap.containsKey(orderId)) {
                    Order order = new Order(
                            rs.getTimestamp("customerarrivaldatetime").toInstant(),
                            rs.getDouble("amountdue"),
                            rs.getDouble("amountpaid"),
                            TableNumber.valueOf(rs.getString("tablenumber")),
                            orderId
                    );
                    orderMap.put(orderId, order);
                }

                // Récupère l'order existant à partir du map
                Order order = orderMap.get(orderId);

                // Crée un DishOrder et l'ajoute à l'ordre
                Dish dish = new Dish(); // Suppose que tu as une classe Dish
                dish.setName(rs.getString("name"));

                DishOrder dishOrder = new DishOrder();
                dishOrder.setDish(dish);
                dishOrder.setQuantityToOrder(Double.valueOf(rs.getInt("quantitytoorder")));
                dishOrder.setPrice(rs.getDouble("price"));

                // Ajoute le dishOrder à l'ordre
                if (order.getDishOrders() == null) {
                    order.setDishOrders(new ArrayList<>());
                }
                order.getDishOrders().add(dishOrder);
            }

            // Convertit la Map en une liste pour les orders
            orders.addAll(orderMap.values());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    @Override
    public void insert(Order toAddE) {

    }

    @Override
    public void update(Long id, Order updateE) {

    }

    @Override
    public Order getById(Long id) {
        String query =
                """
                    SELECT * FROM "order" WHERE id = ?
                """;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Order order = new Order(
                        rs.getTimestamp("customerArrivalDatetime").toInstant(),
                        rs.getDouble("amountDue"),
                        rs.getDouble("amountPaid"),
                        TableNumber.valueOf(rs.getString("tableNumber")),
                        rs.getLong("id")
                );
                order.setDishOrders(getDishOrdersByOrderId(id, connection));
                return order;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<DishOrder> getDishOrdersByOrderId(long orderId, Connection connection) throws SQLException {
        List<DishOrder> dishOrders = new ArrayList<>();
        String query = """
            SELECT d_o.*, d.name, d.id AS dish_id
            FROM dish_order d_o
            JOIN dish d ON d.id = d_o.dish_id
            WHERE orderId = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getLong("dish_id"));
                dish.setName(rs.getString("name"));

                DishOrder dishOrder = new DishOrder(
                        rs.getLong("id"),
                        dish,
                        rs.getDouble("quantityToOrder"),
                        rs.getDouble("price"),
                        null // L'objet Order peut être réinjecté après si besoin
                );
                dishOrders.add(dishOrder);
            }
        }

        return dishOrders;
    }
}
