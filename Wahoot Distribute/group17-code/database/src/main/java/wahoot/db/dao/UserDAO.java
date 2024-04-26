package wahoot.db.dao;
@FunctionalInterface
public interface UserDAO<T> {
    public T getByUsername(String username);

}
