package wahoot.db.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Enumerates the provinces and territories in Canada.
 */
enum Province {
    ONTARIO,
    BRITISH_COLUMBIA,
    ALBERTA,
    SASKATCHEWAN,
    NOVA_SCOTIA,
    NEW_BRUNSWICK,
    YUKON,
    NORTHWEST_TERRITORIES,
    NUNAVUT,
    NEWFOUNDLAND,
    QUEBEC,
    MANITOBA

}

/**
 * Embeddable class representing a location with province, postcode, and address.
 * This class is intended to be embedded in other entity classes.
 */
@Embeddable
class Location {
    @Enumerated(value = EnumType.ORDINAL)
    private Province province;
    private String postcode;
    private String address;

    /**
     * Constructs a new Location instance with specified province, postcode, and address.
     *
     * @param province The province or territory within Canada.
     * @param postcode The postal code of the location.
     * @param address The street address of the location.
     */
    public Location(Province province, String postcode, String address){
      this.province = province;
      this.postcode = postcode;
      this.address = address;
    }
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}