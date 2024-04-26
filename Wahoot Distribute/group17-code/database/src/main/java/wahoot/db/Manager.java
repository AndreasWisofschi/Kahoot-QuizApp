package wahoot.db;

import org.hibernate.annotations.NaturalId;
import wahoot.db.utils.DaoProxy;
import wahoot.db.dao.DAO;
import wahoot.db.dao.DefaultDaoImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class keeps track of all DAO instances and proxy's CRUD operations to the DAO matching
 * the respective entity
 */
public class Manager implements DAO<Object> {

    Map<Class<?>, DefaultDaoImpl<?>> map;

    private Class<DefaultDaoImpl<?>> defaultImpl;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Manager::close));
    }

    /**
     * Manually adds classes to persistence context
     * @param add entity to be added
     * @return new Dao implementation associated with passed in entity
     */
    public DefaultDaoImpl<?> addClass(Class<?> add) {
        DefaultDaoImpl<?> impl;
        if(defaultImpl != null) {
            try {
               impl = defaultImpl.getDeclaredConstructor().newInstance();
                map.put(add, impl);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            impl = new DefaultDaoImpl<>(add);
            map.put(add, impl);
        }
        return impl;
    }

    /**
     * Adds a custom implementation for manager to use
     * @param add Persistence class to add
     * @param impl Implementation to associate with persistence class
     * @param <T> Generic class
     */
    public <T extends DefaultDaoImpl<?>> void addCustomImpl(Class<?> add, T impl) {
        map.put(add, impl);
    }

    /**
     * constructs new manager
     */
    public Manager() {
        map = new HashMap<>();
    }

    /**
     * constructs new manager with given implementation
     * @param defaultImpl implementation to be constructed
     */
    public Manager(Class<DefaultDaoImpl<?>> defaultImpl) {
        this.defaultImpl = defaultImpl;
    }

    /**
     * gets object from database based off id, uses the dao implementation to access database
     * @param id id of object to be returned
     * @return object correlated with id
     */
    @Override
    public Optional<?> get(long id) {
        return map.values()
                .stream()
                .filter((x) -> x.get(id).isPresent())
                .findFirst()
                .flatMap(first -> first.get(id));
    }


    /**
     * delete an entity from database
     * @param id id of object to be deleted
     */
    @Override
    public void delete(long id) {
        DefaultDaoImpl<?> obj = map.values()
                .stream()
                .findFirst()
                .orElseThrow();
        obj.delete(id);
    }

    /**
     * Default implementation, unused in manager
     * @throws RuntimeException not to use this method
     * @return none
     */
    @Override
    public List<Object> getAll() {
        throw new RuntimeException("getAll not supported by Manager please use getAllByClass instead.");
    }

    /**
     * gets all objects of a given class from database
     * @param clazz persistence class to be used as identifier
     * @return all objects related to persistence class
     * @param <T> Generic type class
     */
    public <T> List<T> getAllByClass(Class<T> clazz) {
        DefaultDaoImpl<?> dao = map.get(clazz);
        if(dao == null) {
            dao = addClass(clazz);
        }
        return (List<T>) dao.getAll();
    }

    /**
     * updates data in the database, creates new instance if non-existent
     * @param data data to be updated
     */
    @Override
    @SuppressWarnings("unchecked")
    public void update(Object data) {
        if(!map.containsKey(data.getClass())) {
            addClass(data.getClass());
        }
        DefaultDaoImpl<Object> dao = (DefaultDaoImpl<Object>) map.get(data.getClass());
        dao.update(data);
    }

    /**
     * searches for given object in database
     * @param o Object to be searched for
     * @return Optional of object, empty() if found
     */
    @Override
    public Optional<Object> find(Object o) {
        for(Class<?> clazz: map.keySet()) {
            Optional<?> result = map.get(clazz).find(o);
            if(result.isPresent()) return (Optional<Object>) result;
        }
        return Optional.empty();
    }

    /**
     * Saves an object into the databse
     * @param data data to be saved
     */
    @Override
    @SuppressWarnings("unchecked")
    public void save(Object data) {
        if(!map.containsKey(data.getClass())) {
            addClass(data.getClass());
        }
        DefaultDaoImpl<Object> dao = (DefaultDaoImpl<Object>) map.get(data.getClass());
        dao.save(data);
    }

    /**
     * closes persistence context
     */
    static private void close() {
        DefaultDaoImpl.close();
    }
}
