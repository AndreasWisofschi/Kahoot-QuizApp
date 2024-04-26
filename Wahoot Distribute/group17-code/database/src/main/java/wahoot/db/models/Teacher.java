package wahoot.db.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a teacher entity, extending the {@link User} class. A teacher is associated with a school and can teach multiple classrooms.
 */

@Entity(name = "Teacher")
@DiscriminatorValue(value = AccountType.Types.TEACHER)
@PrimaryKeyJoinColumn(name = "id")
public final class Teacher extends User {
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="school_id")
    private School school;
    @OneToMany(mappedBy = "teacher")
    private Set<Classroom> classes;

    /**
     * Retrieves the classrooms taught by the teacher.
     *
     * @return A set of {@link Classroom} objects.
     */
    public Set<Classroom> getClassesTaught() {
        return classes;
    }

    /**
     * Gets the school to which the teacher is associated.
     *
     * @return The associated {@link School}.
     */
    public School getSchool() {
        return school;
    }

    /**
     * Adds a classroom to the set of classrooms taught by the teacher.
     *
     * @param classroom The {@link Classroom} to be added.
     */
    public void addClass(Classroom classroom) {
        classes.add(classroom);
    }

    /**
     * Constructs a new Teacher instance using the given builder.
     *
     * @param builder The {@link UserBuilder} to construct a new Teacher instance.
     */
    public Teacher(UserBuilder builder) {
        classes = new HashSet<>();
        firstname = builder.firstname;
        lastname = builder.lastname;
        username = builder.username;
        email = builder.email;
        gender = builder.gender;
        password = builder.password;
        type = AccountType.TEACHER;
    }

    /**
     * Constructs a new Teacher instance with specified school.
     *
     * @param builder The {@link UserBuilder} to construct a new Teacher instance.
     * @param school The {@link School} the teacher is associated with.
     */
    public Teacher(UserBuilder builder, School school) {
        classes = new HashSet<>();
        firstname = builder.firstname;
        lastname = builder.lastname;
        username = builder.username;
        email = builder.email;
        gender = builder.gender;
        this.school = school;

    }

    /**
     * Sets the school with which the teacher is associated.
     *
     * @param school The {@link School} to be associated with the teacher.
     */
    public void setSchool(School school) {
        this.school = school;
    }


    public Teacher() {
        classes = new HashSet<>();

    }
}
