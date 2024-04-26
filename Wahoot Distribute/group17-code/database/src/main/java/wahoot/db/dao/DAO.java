package wahoot.db.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<?> get(long id);
    void save(T data);
    void delete(long id);
    List<T> getAll();
    void update(T data);
    Optional<T> find(Object o);

}
