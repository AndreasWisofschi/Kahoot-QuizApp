package wahoot.db.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;
    Province province;
    String postcode;
    String address;

    @BeforeEach
    void setUp() {
        province = Province.ONTARIO;
        address = "123 abc street";
        postcode = "A1A1A1";
        location = new Location(province, postcode, address);
    }

    @Test
    void getProvince() {
        assertEquals(province, location.getProvince());
    }

    @Test
    void setProvince() {
        Province prov = Province.ALBERTA;
        location.setProvince(prov);
        assertEquals(prov, location.getProvince());
    }

    @Test
    void getPostcode() {
        assertEquals(postcode, location.getPostcode());
    }

    @Test
    void setPostcode() {
        location.setPostcode("123456");
        assertEquals("123456", location.getPostcode());
    }

    @Test
    void getAddress() {
        assertEquals(address, location.getAddress());
    }

    @Test
    void setAddress() {
        location.setAddress("New Address");
        assertEquals("New Address", location.getAddress());
    }
}