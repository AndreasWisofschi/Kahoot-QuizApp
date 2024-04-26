package wahoot.db.dao;



import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.annotations.NaturalId;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implementation for manager class, takes in a generic paramterized type
 * @param <T> generic parameterized type
 */
public class DefaultDaoImpl<T> implements DAO<T> {

    public static final String UNIT_NAME = "USER";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIT_NAME);

    private Class<T> persistenceClass;

    private EntityManager manager;

    /**
     * fetches given object from database
     * @param id id of object to fetch
     * @return object corresponding to given ID
     */
    @Override
    public Optional<T> get(long id) {
        return ExecuteInTransaction((Function<EntityManager, T>) entityManager -> entityManager.find(persistenceClass, id));
    }

    /**
     * saves data to the database
     * @param data data to be saved
     */
    @Override
    public void save(T data) {
        ExecuteInTransaction((Consumer<EntityManager>) entityManager -> entityManager.persist(data));
    }

    /**
     * deletes an object from the database
     * @param id id of object to be deleted
     */
    @Override
    public void delete(long id) {
        ExecuteInTransaction((Consumer<EntityManager>) entityManager -> entityManager.remove(id));
    }

    /**
     * gets all entities of a given type specified by manager
     * @return list of all objects of given type
     */
    @Override
    public List<T> getAll() {
        return ExecuteInTransactionMultiResult(entityManager -> {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(persistenceClass);
            Root<T> root = query.from(persistenceClass);
            CriteriaQuery<T> all = query.select(root);
            TypedQuery<T> allQuery = entityManager.createQuery(all);
            return allQuery.getResultList();
        });
    }

    /**
     * updates a value in databse
     * @param data data to be updated
     */
    @Override
    public void update(T data) {
        ExecuteInTransaction((Function<EntityManager, T>) entityManager -> entityManager.merge(data));
    }

    /**
     * finds an object in the database
     * @param o Object to look for
     * @return Optional version of object -> Optional.empty() if not found, else returns object
     */
    @Override
    public Optional<T> find(Object o) {
        for(Field field: persistenceClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(NaturalId.class)) {
                return ExecuteInTransaction((Function<EntityManager, T>) entityManager -> entityManager
                    .unwrap(Session.class)
                    .bySimpleNaturalId(persistenceClass)
                    .load(o)
                );
            }
        }
        return Optional.empty();
    }

    /**
     *Used to directly communicate with database, only used when expecting multiple returns
     * @param consumer Consumer to be executed
     * @return List of objects from database
     */
    protected List<T> ExecuteInTransactionMultiResult(Function<EntityManager, List<T>> consumer) {
        manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        List<T> ret = null;
        try {
            transaction.begin();
            ret = consumer.apply(manager);
            transaction.commit();
        }
        catch(RuntimeException e) {
            transaction.rollback();
        }
        finally {
            manager.close();
        }
        return ret;
    }


    /**
     * Equivalent of the above method, used with methods like find that
     * may or may not have a return value
     * @param consumer Consumer to be executed
     * @return Optional of item fetched from database
     */
    protected Optional<T> ExecuteInTransaction(Function<EntityManager, T> consumer) {
        manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        T ret = null;
        try {
            transaction.begin();
            ret = consumer.apply(manager);
            transaction.commit();
        }
        catch(RuntimeException e) {
            transaction.rollback();
        }
        finally {
            manager.close();
        }
        return Optional.ofNullable(ret);
    }

    /**
     * Same as previous methods, used when return value isnt needed
     * would be used with a function like save or update
     * @param consumer Consumer to be executed
     */
    protected void ExecuteInTransaction(Consumer<EntityManager> consumer) {
        manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        T ret = null;
        try {
            transaction.begin();
            consumer.accept(manager);
            transaction.commit();
        }
        catch(RuntimeException e) {
            transaction.rollback();
        }
        finally {
            manager.close();
        }
    }


    /**
     * Constructs a new DAO implementation
     * @param persistenceClass Entity the Dao is intended for
     */
    public DefaultDaoImpl(Class<T> persistenceClass) {
        this.persistenceClass = persistenceClass;
    }

    /**
     * getter for persistence class
     * @return persistence class
     */
    public Class<T> getPersistenceClass(){
        return this.persistenceClass;
    }

    /**
     * constructor for new Dao
     */
    public DefaultDaoImpl() {
        super();
    }

    /**
     * closes persistence context
     */
    static public void close() {
        factory.close();
    }
}
