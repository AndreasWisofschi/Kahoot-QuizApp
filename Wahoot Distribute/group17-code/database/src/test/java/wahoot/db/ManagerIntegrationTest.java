package wahoot.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wahoot.db.dao.DefaultDaoImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerIntegrationTest {

    private Manager manager;
    @Mock
    private DefaultDaoImpl<Object> daoMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manager = new Manager();
        manager.map = new HashMap<>();
        manager.map.put(Object.class, daoMock);
    }



    @Test
    void get_existingId() {
        long id = 1;

        Object obj = new Object();
        when(daoMock.get(id)).thenReturn(Optional.of(obj));

        Optional<?> result = manager.get(id);

        assertTrue(result.isPresent());
        assertEquals(obj, result.get());
    }

    @Test
    void get_nonexistentId() {
        long id = 1;

        when(daoMock.get(id)).thenReturn(Optional.empty());

        Optional<?> result = manager.get(id);

        assertFalse(result.isPresent());
    }

    @Test
    void addClass_successful(){
        Class<Object> clazz = Object.class;

        DefaultDaoImpl<?> result = manager.addClass(clazz);

        assertEquals(result, manager.map.get(clazz));

    }

    @Test
    void delete_existingId(){

        long id = 1;

        manager.delete(id);
        verify(daoMock, times(1)).delete(id);
    }

    @Test
    void delete_nonExistentId(){
        long id = 1;

        doNothing().when(daoMock).delete(id);
        manager.delete(id);

        verify(daoMock).delete(id);
    }

    @Test
    void getAllByClass_SuccessfulReturn(){
        Object obj1 = new Object();
        Object obj2 = new Object();
        List<Object> expectedObjects = Arrays.asList(obj1, obj2);

        when(daoMock.getAll()).thenReturn(expectedObjects);

        List<Object> result = manager.getAllByClass(Object.class);

        assertEquals(expectedObjects, result);
    }

    @Test
    void getAllByClass_UnsuccessfulReturn(){
        Object obj1 = new Object();
        Object obj2 = new Object();
        List<Object> expectedObjects = Arrays.asList(obj1, obj2);

        when(daoMock.getAll()).thenReturn(null);

        List<Object> result = manager.getAllByClass(Object.class);

        assertNull(result);
    }

    @Test
    void update(){
        Object obj = new Object();

        manager.update(obj);

        verify(daoMock, times(1)).update(obj);
    }
    @Test
    void find_successful(){

        Object obj = new Object();

        when(daoMock.find(obj)).thenReturn(Optional.of(obj));
        Optional<?> result = manager.find(obj);

        assertTrue(result.isPresent());
        assertEquals(result.get(), obj);

    }

    @Test
    void find_unsuccessful(){
        Object obj = new Object();

        when(daoMock.find(obj)).thenReturn(Optional.empty());
        Optional<?> result = manager.find(obj);

        assertFalse(result.isPresent());
    }


    @Test
    void save(){
        Object obj = new Object();

        manager.save(obj);

        verify(daoMock, times(1)).save(obj);

    }


}
