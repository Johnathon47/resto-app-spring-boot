package components.service;

import components.model.Order;
import components.repository.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> getAll() {
        return orderDao.getAll(0,5);
    }

    public Order getById(Long id) {
        return orderDao.getById(id);
    }
}
