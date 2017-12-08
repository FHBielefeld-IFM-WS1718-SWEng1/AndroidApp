package partyplaner.data.user;

import android.media.Image;

import java.util.GregorianCalendar;

/**
 * Created by André on 24.11.2017.
 */

public class User {

    private String email;
    private String name;
    private String surname;
    private String password;
    private GregorianCalendar birthday;
    private String gender;
    private String address;
    private Image profilePicture;

    public User(String email, String name, String surname, String password,
                GregorianCalendar birthday, String gender, String address, Image profilePicture) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public static User createTestUser() {
        return new User("tsm@fh-bielefeld.de", "Tim", "Meier",
                "tsm", new GregorianCalendar(1954, 7, 17), "(Fe)Male",
                "Tim-Steven-Meier-Straße 1, 12345 Timstadt", null);
    }
}
