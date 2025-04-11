package components.service;

import components.model.DishOrder;
import components.repository.dao.DishOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishOrderService {
    private DishOrderDao dishOrderDao;

    @Autowired
    public DishOrderService(DishOrderDao dishOrderDao) {
        this.dishOrderDao = dishOrderDao;
    }

    public List<DishOrder> getAllDishOrder() {
        return dishOrderDao.getAll(0,5);
    }
}
