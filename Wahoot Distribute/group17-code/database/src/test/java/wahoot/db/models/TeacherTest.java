package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    private Teacher teacher;
    private School school;
    @BeforeEach
    void setUp() {
        UserBuilder b = new UserBuilder()
                .setFirstName("Dan")
                .setLastName("Cameron")
                .setGender(Gender.MALE)
                .setEmail("dcamer45@uwo.ca");
        school = new School(1, "Western");
        teacher = new Teacher(b, school);
    }

    @Test
    void addClass() {
        Classroom classroom = new Classroom("ABC");
        Classroom classroom2 = new Classroom("DEF");
        Set<Classroom> classes = new HashSet<>();
        classes.add(classroom);
        classes.add(classroom2);
        teacher.addClass(classroom);
        teacher.addClass(classroom2);
        assertEquals(classes, teacher.getClassesTaught());
    }
    @Test
    void getClassesTaught() {
        Classroom classroom = new Classroom("ABC");
        Classroom classroom2 = new Classroom("DEF");
        Set<Classroom> classes = new HashSet<>();
        classes.add(classroom);
        classes.add(classroom2);
        teacher.addClass(classroom);
        teacher.addClass(classroom2);
        assertEquals(classes, teacher.getClassesTaught());
    }

    @Test
    void getSchool() {
        assertEquals(school, teacher.getSchool());
    }


    @Test
    void setSchool() {
        School school = new School(2, "New Western");
        teacher.setSchool(school);
        assertEquals(school, teacher.getSchool());
    }
}