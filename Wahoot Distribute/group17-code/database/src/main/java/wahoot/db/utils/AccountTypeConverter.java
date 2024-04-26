package wahoot.db.utils;

import wahoot.db.models.AccountType;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * converter that allows the AccountType ENUM to be stored in the database column
 */
@Converter
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    /**
     * converts ENUM into its string equivalent
     * @param accountType  the entity attribute value to be converted
     * @return String of account type in proper form
     */
    @Override
    public String convertToDatabaseColumn(AccountType accountType) {
        if(accountType == null) return null;
        return accountType.getType();
    }

    /**
     * Converts database string back into ENUM
     * only uses proper form for conversion
     * @param s  the data from the database column to be
     *                converted
     * @return the corresponding ENUM from the database
     */
    @Override
    public AccountType convertToEntityAttribute(String s) {
        if(s == null) return null;
        return AccountType.fromType(s);
    }
}
