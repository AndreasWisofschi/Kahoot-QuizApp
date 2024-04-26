package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class ClassroomTest {

    private Classroom classroom;
    private Student student1;

    private Student student2;

    private Teacher teacher;


    @BeforeEach
    void setUp(){
        this.classroom = new Classroom("abc123");
        UserBuilder b = new UserBuilder()
            .setFirstName("Dan")
            .setLastName("Cameron")
            .setGender(Gender.MALE)
            .setEmail("dcamer45@uwo.ca");

        UserBuilder b2 = new UserBuilder()
            .setFirstName("John")
            .setLastName("Smith")
            .setGender(Gender.FEMALE)
            .setEmail("abc@uwo.ca");

        UserBuilder b3 = new UserBuilder()
                .setFirstName("Mr")
                .setLastName("C")
                .setGender(Gender.NON_BINARY)
                .setEmail("aaa@uwo.ca");

        student1 = b.buildStudent();
        student2 = b2.buildStudent();
        teacher = b3.buildTeacher();

    }

    @Test
    void addStudent(){
        classroom.addStudent(student1);
        classroom.addStudent(student2);
        Set<Student> students = classroom.getStudents();
        assertEquals(students, classroom.getStudents());
    }

    @Test
    void addAllStudents(){
        List<Student> studentSet = new LinkedList<>();
        studentSet.add(student1);
        studentSet.add(student2);
        classroom.addAllStudents(studentSet);
        Set<Student> allStudents = new HashSet<>(studentSet);
        assertEquals(allStudents, classroom.getStudents());
    }

    @Test
    void getStudents() {
        Set<Student> students = classroom.getStudents();
        classroom.addStudent(student1);
        classroom.addStudent(student2);
        assertEquals(students, classroom.getStudents());
    }

    @Test
    void addQuiz(){
        Quiz quiz = new Quiz();
        Quiz quiz2 = new Quiz();
        Set<Quiz> q = new HashSet<>();
        q.add(quiz);
        q.add(quiz2);
        classroom.addQuiz(quiz);
        classroom.addQuiz(quiz2);
        assertEquals(q, classroom.getQuizzes());
    }

    @Test
    void getQuiz(){
        Quiz quiz = new Quiz();
        Quiz quiz2 = new Quiz();
        Set<Quiz> q = new HashSet<>();
        q.add(quiz);
        q.add(quiz2);
        classroom.addQuiz(quiz);
        classroom.addQuiz(quiz2);
        assertEquals(q, classroom.getQuizzes());
    }

    @Test
    void setSubject(){
        classroom.setSubject(Subject.MATH);
        assertEquals(Subject.MATH, classroom.getSubject());
    }
    @Test
    void getSubject(){
        classroom.setSubject(Subject.MATH);
        assertEquals(Subject.MATH, classroom.getSubject());
    }

    @Test
    void getTeacher() {
        Classroom c = new Classroom("abc", teacher);
        assertEquals(teacher, c.getTeacher());
    }

    @Test
    void getClassCode() {
        assertEquals("abc123", classroom.getClassCode());
    }
    @Test
    void setClassName(){
        classroom.setName("Computer Science");
        assertEquals("Computer Science", classroom.getClassName());
    }
    @Test
    void getClassName() {
        classroom.setName("Computer Science");
        assertEquals("Computer Science", classroom.getClassName());
    }
}