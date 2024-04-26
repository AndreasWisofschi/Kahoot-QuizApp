package wahoot.db.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;
import wahoot.db.utils.AccountTypeConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Abstract base class for all user entities within the application.
 * This class is mapped to the "User" table in the database and provides common attributes like username, password, email, etc.,
 */

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
@Entity(name = "User")
@Table(name= "User")
abstract public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_Sequence")
    @SequenceGenerator(name = "ID_Sequence", initialValue = 1000000, allocationSize = 1)
    private long id;
    @Convert(converter = AccountTypeConverter.class)
    @Column(name = "type", insertable=false, updatable=false)
    protected AccountType type;
    protected String firstname;
    protected String lastname;
    @NaturalId
    @Column(unique = true)
    protected String username;
    protected String password;
    @Column(unique = true)
    protected String email;

    @Enumerated(value = EnumType.STRING)
    protected Gender gender;

    @Embedded
    private Location location;

    /**
     * Retrieves the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the location associated with the user.
     *
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's ID.
     */
    public long getID() {
        return id;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return The user's account type (teacher,student,admin)
     */
    public AccountType getAccountType() {
        return type;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param id The new ID for the user.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return The user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param password The new ID for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return The new email for the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return The new gender for the user.
     */
    public Gender getGender() {
        return gender;
    }
    /**
     * Sets the unique identifier for the user.
     *
     * @param location The new location for the user.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}

