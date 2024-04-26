package wahoot.db.models;

import jakarta.persistence.*;


import java.util.*;

/**
 * Represents a student entity in the system. A student is a type of user that can be enrolled in multiple classrooms
 * and belongs to a specific school. This class extends the {@link User} class, inheriting user properties such as
 * firstname, lastname, username, email, and password.
 */

@Entity(name = "Student")
@DiscriminatorValue(value = AccountType.Types.STUDENT)
@PrimaryKeyJoinColumn(name = "id")
public final class Student extends User {
    @ManyToMany(mappedBy = "students")
    private Set<Classroom> classes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    /**
     * Gets the school that the student is enrolled in.
     *
     * @return The school entity associated with the student.
     */
    private long score;

    private int quizzesCompleted;

    /**
     * Represents an observation of a student's score on a test
     * used in progress tracker to make graph
     */
    @Embeddable
    public static class Observation {
        private Date date;

        private double score;

        /**
         * getter for test score
         * @return score on given day
         */
        public double getScore() {
            return score;
        }

        /**
         * getter for date
         * @return date when score was logged
         */
        public Date getDate() {
            return date;
        }

        /**
         * constructs an observation of a date and score to be used in grph
         * @param date certain day when a score was logged
         * @param score numerical value of score that was logged
         */
        public Observation(Date date, double score) {
            this.date = date;
            this.score = score;
        }

        public Observation() {

        }

    }
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "Obeservations",
            joinColumns = @JoinColumn(name = "student_id")
    )
    private List<Observation> observations;



    public long getScore() {
        return score;
    }

    /**
     * adds observation to list of that student's observations
     * @param observation
     */
    public void addObservation(Observation observation) {
        observations.add(observation);
    }

    /**
     * getter for observations
     * @return all observations a given student has
     */
    public List<Observation> getObservations() {
        return observations;
    }

    /**
     * adds to the total score (experience points)
     * @param score extra points to be added
     */
    public void addScore(long score) {
        this.score += score;
        this.quizzesCompleted += 1;
    }

    /**
     * getter for amount of completed quizzes
     * @return number of quizzes that student completed
     */
    public int getQuizzesCompleted() {
        return quizzesCompleted;
    }

    /**
     * gettter for school
     * @return the school that student goes to
     */
    public School getSchool() {
        return school;
    }

    /**
     * Associates a school with the student.
     *
     * @param school The school to be associated with the student.
     */
    public void addSchool(School school){
        this.school = school;
    }

    /**
     * Retrieves the set of classrooms that the student is enrolled in.
     *
     * @return A set of Classroom entities.
     */
    public Set<Classroom> getClasses() {
        return classes;
    }

    /**
     * Constructs a new Student instance using the given builder.
     *
     * @param builder The UserBuilder to construct a new Student instance.
     */
    public Student(UserBuilder builder) {
        this();
        firstname = builder.firstname;
        lastname = builder.lastname;
        username = builder.username;
        email = builder.email;
        gender = builder.gender;
        password = builder.password;
        classes = new HashSet<>();
        type = AccountType.STUDENT;
    }

    public long getLevel() {
        if(score < 300) return 0;
        double a = 0.10;
        return (long) ( a * Math.sqrt(score - 300));
    }
    public long checkLevel(){
        double lvl = Math.log((double) score/1000) / Math.log(1.5);
        return Math.round(lvl);
    }

    /**
     * Adds a classroom to the set of classrooms that the student is enrolled in.
     *
     * @param classroom The classroom to add.
     */
    public void addClass(Classroom classroom) {
        classes.add(classroom);
    }

    public Student() {
        classes = new HashSet<>();
        observations = new LinkedList<>();
    }
}
