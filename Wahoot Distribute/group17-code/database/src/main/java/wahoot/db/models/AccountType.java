package wahoot.db.models;

/**
 * Represents the types of accounts in the application, between student, teacher, and administrator roles.
 * This enum provides a method to convert from a string representation to an enum instance and to retrieve the type's string representation.
 */
public enum AccountType {
    STUDENT(Types.STUDENT),
    TEACHER(Types.TEACHER),
    ADMINISTRATOR(Types.ADMINISTRATOR);
    private final String type;


    /**
     * Retrieves the string representation of the account type.
     *
     * @return A {@link String} representing the account type.
     */
    public String getType() {
        return type;
    }



    /**
     * Converts a string value to its corresponding {@link AccountType} enum.
     *
     * @param s The string representation account type.
     * @return The {@link AccountType} enum corresponding to the string val.
     * @throws EnumConstantNotPresentException if the string does not match any stated account type.
     */

    public static AccountType fromType(String s) {
        switch(s) {
            case Types.STUDENT -> {
                return STUDENT;
            }
            case Types.ADMINISTRATOR -> {
                return ADMINISTRATOR;
            }
            case Types.TEACHER -> {
                return TEACHER;
            }

            default -> throw new EnumConstantNotPresentException(AccountType.class, s);
        }
    }

    /**
     * Constructs an {@link AccountType} enum with the type.
     * @param type The string representation of the account type.
     */
    AccountType(String type) {
        this.type = type;
    }

    public static class Types {
        public static final String STUDENT = "Student";
        public static final String TEACHER = "Teacher";

        public static final String ADMINISTRATOR = "Administrator";
    }

}
