package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        UserBuilder b = new UserBuilder()
                .setFirstName("Dan")
                .setLastName("Cameron")
                .setGender(Gender.MALE)
                .setEmail("dcamer45@uwo.ca");
        student = b.buildStudent();
    }
    @Test
    void getSchool() {
        School school = new School(1, "Western");
        student.addSchool(school);
        assertEquals(school, student.getSchool());
    }

    @Test
    void addSchool(){
        School school = new School(1, "Western");
        student.addSchool(school);
        assertEquals(school, student.getSchool());
    }
    @Test
    void getClasses() {
        Classroom classroom = new Classroom("ABC");
        Classroom classroom2 = new Classroom("DEF");
        Set<Classroom> classes = new HashSet<>();
        classes.add(classroom);
        classes.add(classroom2);
        student.addClass(classroom);
        student.addClass(classroom2);
        assertEquals(classes, student.getClasses());
    }


    @Test
    void addClasses(){
        Classroom classroom = new Classroom("ABC");
        Classroom classroom2 = new Classroom("DEF");
        Set<Classroom> classes = new HashSet<>();
        classes.add(classroom);
        classes.add(classroom2);
        student.addClass(classroom);
        student.addClass(classroom2);
        assertEquals(classes, student.getClasses());
    }

    @Test
    void addScore(){
        student.addScore(1000);
        student.addScore(2000);
        assertEquals(3000, student.getScore());
    }

    @Test
    void getScore(){
        student.addScore(1000);
        assertEquals(1000, student.getScore());
    }
    @Test
    void checkLevel(){
        student.addScore(1500);
        assertEquals(1, student.checkLevel());
        student.addScore(10000);
        assertTrue(student.getScore() > 4);
    }


    @Test
    void addObservation(){
        Date date = new Date();
        Student.Observation observation = new Student.Observation(date, 2000);
        student.addObservation(observation);
        List<Student.Observation> observationList = new LinkedList<>();
        observationList.add(observation);
        assertEquals(observationList, student.getObservations());
    }

    @Test
    void getObservations(){
        Date date = new Date();
        Student.Observation observation = new Student.Observation(date, 2000);
        student.addObservation(observation);
        List<Student.Observation> observationList = new LinkedList<>();
        observationList.add(observation);
        assertEquals(observationList, student.getObservations());
    }

}