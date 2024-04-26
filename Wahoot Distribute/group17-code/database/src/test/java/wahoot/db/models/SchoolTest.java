package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

    private School school;
    @BeforeEach
    void setUp(){
        school = new School(1, "Western");
    }

    @Test
    void getName() {
        assertEquals("Western", school.getName());
    }

    @Test
    void setName() {
        school.setName("New Western");
        assertEquals("New Western", school.getName());
    }

    @Test
    void getLocation() {
        Province province = Province.ONTARIO;
        Location loc = new Location(province, "N6H1R7", "123 abc street");
        school.setLocation(loc);
        assertEquals(loc, school.getLocation());
    }

    @Test
    void setLocation() {
        Province province = Province.ONTARIO;
        Location loc = new Location(province, "N6H1R7", "123 abc street");
        school.setLocation(loc);
        assertEquals(loc, school.getLocation());
    }

    @Test
    void getOrganization() {
        String org = "Company";
        school.setOrganization(org);
        assertEquals(org, school.getOrganization());
    }

    @Test
    void setOrganization() {
        String org = "Company";
        school.setOrganization(org);
        assertEquals(org, school.getOrganization());
    }

    @Test
    void addClass() {
        Classroom classroom = new Classroom("ABC");
        Classroom classroom2 = new Classroom("DEF");
        Set<Classroom> classes = new HashSet<>();
        classes.add(classroom);
        classes.add(classroom2);
        school.addClass(classroom);
        school.addClass(classroom2);
        assertEquals(classes, school.getClasses());
    }

    @Test
    void getSchoolID() {
         assertEquals(1, school.getSchoolID());
    }
}