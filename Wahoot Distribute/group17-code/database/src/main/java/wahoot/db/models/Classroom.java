package wahoot.db.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a classroom entity in the application.
 * Classrooms are unique entities that contain quizzes, are assigned a teacher, and have enrolled students.
 */

@Entity
public class Classroom {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @NaturalId
    @Column(nullable = false, unique = true)
    private String code;
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="teacher")
    private Teacher teacher;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Classroom_Students",
            joinColumns = { @JoinColumn(name = "classroom_id") },
            inverseJoinColumns = { @JoinColumn(name = "student_id") }
    )
    private Set<Student> students;

    @OneToMany(mappedBy="room")
    private Set<Quiz> quizzes;

    @Enumerated(value = EnumType.STRING)
    protected Subject subject;

    /**
     * Gets the set of quizzes associated with the classroom.
     * @return A set of {@link Quiz} objects.
     */
    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    /**
     * Adds a quiz to the classroom.
     * @param quiz The {@link Quiz} to be added.
     */
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    /**
     * Adds student to the classroom
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    public void addAllStudents(List<Student> all) {
        students.addAll(all);
    }

    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets the subject for the classroom.
     * @param subject The {@link Subject} to be set.
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Gets the set of students enrolled in the classroom.
     * @return A set of {@link Student} objects.
     */
    public Set<Student> getStudents() {
        return students;
    }

    /**
     * Gets the teacher assigned to the classroom.
     * @return The assigned {@link Teacher}.
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * Gets the unique code of the classroom.
     * @return The classroom code.
     */
    public String getClassCode() {
        return code;
    }

    /**
     * Gets the name of the classroom.
     * @return The classroom name.
     */
    public String getClassName() {
        return name;
    }

    /**
     * Sets the name of the classroom.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    public Classroom(String code, String name) {
        this();
        this.code = code;
        this.name = name;
    }

    public Classroom() {
        students = new HashSet<>();
        quizzes = new HashSet<>();
    }

    /**
     * Constructs a Classroom with a specific code and assigns a teacher to it.
     * Enrolls the classroom into the teacher's class list.
     *
     * @param code The unique code of the classroom.
     * @param teacher The teacher assigned to the classroom.
     */
    public Classroom(String code, Teacher teacher) {
        this();
        this.code = code;
        this.teacher = teacher;
        teacher.addClass(this);
    }

    /**
     * Indicates if other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return id == classroom.id;
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, teacher, students, quizzes, subject);
    }

    /**
     * Constructs a Classroom with a specific code (code will be a 5 digit random combination.
     * @param code The unique code of the classroom.
     */
    public Classroom(String code) {
        this();
        this.code = code;
    }


}
