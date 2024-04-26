package wahoot.db.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDaoImplTest {

    @Mock
    private UserDaoImpl<Object> userMock;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByUsername() {

        when(userMock.getByUsername("abc")).thenReturn(null);

        assertNull(userMock.getByUsername("abc"));


    }
}