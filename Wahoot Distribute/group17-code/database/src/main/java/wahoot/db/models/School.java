package wahoot.db.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a school entity, including its name, organization, location, and associated classrooms.
 */

@Entity
public class School{
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long ID;

    @Column(unique = true)
    private String name;
    private String organization;

    @OneToMany
    @JoinColumn(name="school_id")
    Set<Classroom> classes;

    @Embedded
    private Location location;

    /**
     * Gets the name of the school.
     *
     * @return The school's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the school.
     *
     * @param name The name to set for the school.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the location of the school.
     *
     * @return The school's location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the school.
     *
     * @param loc The location to set for the school.
     */
    public void setLocation(Location loc){
        this.location = loc;
    }

    /**
     * Gets the organization of the school.
     *
     * @return The school's organization.
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Sets the organization of the school.
     *
     * @param org The organization to set for the school.
     */
    public void setOrganization(String org){
        this.organization = org;
    }

    /**
     * Adds a classroom to the school.
     *
     * @param classroom The classroom to add to the school.
     */
    public void addClass(Classroom classroom){
        classes.add(classroom);
    }

    /**
     * Gets the set of classrooms associated with the school.
     *
     * @return The set of classrooms.
     */
    public Set<Classroom> getClasses(){
        return classes;
    }

    /**
     * Gets the unique identifier of the school.
     *
     * @return The school's ID.
     */
    public long getSchoolID() {
        return ID;
    }

    public School() {

    }

    /**
     * Constructs a new School with the specified name and organization.
     *
     * @param name The name of the school.
     * @param organization The organization of the school.
     */
    public School(String name, String organization) {
            classes = new HashSet<>();
            this.name = name;
            this.organization = organization;
    }

    /**
     * Constructs a new School with the specified ID and name.
     *
     * @param ID The unique identifier for the school.
     * @param name The name of the school.
     */
    public School(long ID, String name) {
        classes = new HashSet<>();
        this.ID = ID;
        this.name = name;
    }
}
