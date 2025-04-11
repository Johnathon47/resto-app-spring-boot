package components.repository.crudOperation;

import java.util.List;

public interface CrudOperation<E> {
    List<E> getAll(int offset, int limit);
    void insert(E toAddE);
    void update(Long id, E updateE);
    E getById(Long id);
}
