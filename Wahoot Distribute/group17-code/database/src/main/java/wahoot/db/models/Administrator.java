package wahoot.db.models;

import jakarta.persistence.*;

/**
 * Represents an administrator in the system, extending the {@link User} class.
 * Administrators have additional privileges.
 * This class maps to the "Administrator" table.
 */

@Entity(name = "Administrator")
@DiscriminatorValue(value = AccountType.Types.ADMINISTRATOR)
@PrimaryKeyJoinColumn(name = "id")
public final class Administrator extends User {
    @Enumerated(value = EnumType.STRING)
    private Rank rank;
    enum Rank {
        MOD,
        SUPER_MOD,
        ADMIN,
        SUPER_ADMIN
    }

    /**
     * Constructs an Administrator using a {@link UserBuilder}, initializing with the builder's values.
     * This constructor allows for setting administrator-specific fields from a UserBuilder instance.
     * @param builder The UserBuilder instance containing values for initializing the Administrator.
     */
    public Administrator(UserBuilder builder) {
        firstname = builder.firstname;
        lastname = builder.lastname;
        username = builder.username;
        email = builder.email;
        gender = builder.gender;
        type = AccountType.ADMINISTRATOR;
        password = builder.password;
    }


    public Administrator() {

    }
}

