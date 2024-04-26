package wahoot.db.models;

/**
 * A builder class for creating {@link User} instances. Supports API style for setting properties.
 */

public class UserBuilder {

    String firstname;
    String lastname;
    String username;
    String email;
    String password;
    Gender gender;

    /**
     * Sets the username for the user.
     *
     * @param username The username to set.
     * @return The current instance of {@link UserBuilder}.
     */
    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the email for the user.
     *
     * @param email The email to set.
     * @return The current instance of {@link UserBuilder}.
     */
    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the first name for the user.
     *
     * @param firstname The first name to set.
     * @return The current instance of {@link UserBuilder}.
     */
    public UserBuilder setFirstName(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * Sets the gender for the user.
     *
     * @param gender The gender to set.
     * @return The current instance of {@link UserBuilder}.
     */
    public UserBuilder setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * Sets the last name for the user.
     *
     * @param lastname The last name to set.
     * @return The current instance of {@link UserBuilder}.
     */
    public UserBuilder setLastName(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * Sets the password for the user.
     *
     * @param password The password to set.
     * @return The current instance of {@link UserBuilder}.
     */
    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Builds a {@link User} instance of the specified type.
     *
     * @param type The {@link AccountType} of the user.
     * @return A new instance of {@link User}, {@link Student}, {@link Teacher}, or {@link Administrator}, depending on the type.
     */
    public User build(AccountType type) {
        return switch (type) {
            case STUDENT -> new Student(this);
            case ADMINISTRATOR -> new Administrator(this);
            case TEACHER -> new Teacher(this);
        };
    }

    /**
     * Builds a {@link Teacher} instance.
     *
     * @return A new instance of {@link Teacher}.
     */
    public Teacher buildTeacher() {
        return new Teacher(this);
    }

    /**
     * Builds a {@link Student} instance.
     *
     * @return A new instance of {@link Student}.
     */
    public Student buildStudent() {
        return new Student(this);
    }

    /**
     * Builds an {@link Administrator} instance.
     *
     * @return A new instance of {@link Administrator}.
     */
    public Administrator buildAdministrator() {
        return new Administrator(this);
    }

    public UserBuilder() {
    }
}

