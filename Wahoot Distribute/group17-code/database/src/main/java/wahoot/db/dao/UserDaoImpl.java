package wahoot.db.dao;

public class UserDaoImpl<T> extends DefaultDaoImpl<T> implements UserDAO<T>{
    public T getByUsername(String username) {
        return null;
    }


    public UserDaoImpl(Class<T> clazz) {
        super(clazz);
    }

    public UserDaoImpl() {

    }


}
