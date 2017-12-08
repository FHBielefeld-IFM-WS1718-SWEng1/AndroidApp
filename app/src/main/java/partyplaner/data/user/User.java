package partyplaner.data.user;

import android.media.Image;

import java.util.GregorianCalendar;

/**
 * Created by André on 24.11.2017.
 */

public class User {

    private String email;
    private String name;
    private String password;
    private GregorianCalendar birthday;
    private String gender;
    private Image profilePicture;

    public User(String email, String name, String password,
                GregorianCalendar birthday, String gender, Image profilePicture) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
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

    public Image getProfilePicture() {
        return profilePicture;
    }

    public static User createTestUser() {
        return new User("tsm@fh-bielefeld.de", "Tim",
                "tsm", new GregorianCalendar(1954, 7, 17), "(Fe)Male",
                 null);
    }
}
