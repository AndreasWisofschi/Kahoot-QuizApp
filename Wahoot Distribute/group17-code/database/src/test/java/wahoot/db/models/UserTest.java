package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest{

    UserBuilder b;
    User student;
    User teacher;
    User admin;

    @BeforeEach
    void setUp(){
        b = new UserBuilder()
                .setFirstName("Dan")
                .setLastName("C")
                .setUsername("dc")
                .setEmail("dc@uwo.ca")
                .setGender(Gender.MALE);



        student = b.build(AccountType.STUDENT);
        teacher = b.build(AccountType.TEACHER);
        admin = b.build(AccountType.ADMINISTRATOR);


    }

    @Test
    void getFirstname() {
        assertEquals("Dan", student.getFirstname());
        assertEquals("Dan", teacher.getFirstname());
        assertEquals("Dan", admin.getFirstname());
    }

    @Test
    void getLastname() {
        assertEquals("C", student.getLastname());
        assertEquals("C", teacher.getLastname());
        assertEquals("C", admin.getLastname());
    }
    @Test
    void getUsername() {
        assertEquals("dc", student.getUsername());
        assertEquals("dc", teacher.getUsername());
        assertEquals("dc", admin.getUsername());
    }
    @Test
    void getEmail() {
        assertEquals("dc@uwo.ca", student.getEmail());
        assertEquals("dc@uwo.ca", teacher.getEmail());
        assertEquals("dc@uwo.ca", admin.getEmail());
    }

    @Test
    void getGender() {
        assertEquals(Gender.MALE, student.getGender());
        assertEquals(Gender.MALE, teacher.getGender());
        assertEquals(Gender.MALE, admin.getGender());
    }
    @Test
    void setLocation() {
        Province province = Province.ALBERTA;
        Location location = new Location(province, "ABC", "DEF");
        student.setLocation(location);
        teacher.setLocation(location);
        admin.setLocation(location);
        assertEquals(location, student.getLocation());
        assertEquals(location, teacher.getLocation());
        assertEquals(location, admin.getLocation());
    }
    @Test
    void getLocation() {
        Province province = Province.ALBERTA;
        Location location = new Location(province, "ABC", "DEF");
        student.setLocation(location);
        teacher.setLocation(location);
        admin.setLocation(location);
        assertEquals(location, student.getLocation());
        assertEquals(location, teacher.getLocation());
        assertEquals(location, admin.getLocation());
    }

    @Test
    void setId() {
        student.setId(1);
        teacher.setId(1);
        admin.setId(1);
        assertEquals(1, student.getID());
        assertEquals(1, teacher.getID());
        assertEquals(1, admin.getID());
    }

    @Test
    void getID() {
        student.setId(1);
        teacher.setId(1);
        admin.setId(1);
        assertEquals(1, student.getID());
        assertEquals(1, teacher.getID());
        assertEquals(1, admin.getID());
    }

    @Test
    void getAccountType() {
        assertEquals(AccountType.STUDENT, student.getAccountType());
        assertEquals(AccountType.TEACHER, teacher.getAccountType());
        assertEquals(AccountType.ADMINISTRATOR, admin.getAccountType());
    }
    @Test
    void setPassword() {
        student.setPassword("abc");
        teacher.setPassword("abc");
        admin.setPassword("abc");
        assertEquals("abc", student.getPassword());
        assertEquals("abc", teacher.getPassword());
        assertEquals("abc", admin.getPassword());
    }
    @Test
    void getPassword() {
        student.setPassword("abc");
        teacher.setPassword("abc");
        admin.setPassword("abc");
        assertEquals("abc", student.getPassword());
        assertEquals("abc", teacher.getPassword());
        assertEquals("abc", admin.getPassword());

    }






}