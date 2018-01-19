package partyplaner.data.user;

/**
 * Created by André on 15.12.2017.
 */

public enum Gender {

    NULL("Nicht angegeben"),
    MALE("Männlich"),
    FEMALE("Weiblich"),
    DIFFERENT("Andere"),
    KAMPFHUBSCHRAUBER("Helisexuell");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public static String getGenderNameByID(Integer id) {
        if (id.equals(null) || id >= Gender.values().length) {
            return NULL.name;
        }
        return  Gender.values()[id].name;
    }
}
