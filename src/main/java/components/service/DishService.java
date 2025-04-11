package components.service;

import components.model.Dish;
import components.repository.dao.DishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private DishDao dishDao;

    @Autowired
    public DishService (DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public List<Dish> getAllDish() {
        return dishDao.getAll(0,5);
    }
}
