package wahoot.db.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import wahoot.db.models.AccountType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AccountTypeConverterTest {

    @Mock
    private AccountTypeConverter mockConverter;


    @BeforeEach
    void setUp() {
        mockConverter = mock(AccountTypeConverter.class);
    }

    @Test
    void convertToDatabaseColumn_successful() {

        AccountType type = AccountType.STUDENT;

        when(mockConverter.convertToDatabaseColumn(type)).thenReturn(AccountType.STUDENT.getType());

        assertEquals(type.getType(), mockConverter.convertToDatabaseColumn(type));
    }
    @Test
    void convertToDatabaseColumn_unsuccessful() {

        AccountType type;

        when(mockConverter.convertToDatabaseColumn(type = null)).thenReturn(null);

        assertNull(mockConverter.convertToDatabaseColumn(type));
    }
    @Test
    void convertToEntityAttribute_successful() {
        String str = "Student";

        when(mockConverter.convertToEntityAttribute(str)).thenReturn(AccountType.fromType(str));

        assertEquals(AccountType.STUDENT, mockConverter.convertToEntityAttribute(str));
    }

    @Test
    void convertToEntityAttribute_unsuccessful() {

        when(mockConverter.convertToEntityAttribute(anyString())).thenThrow(EnumConstantNotPresentException.class);

        assertThrows(EnumConstantNotPresentException.class, () -> mockConverter.convertToEntityAttribute("STUDENT"));
    }
}