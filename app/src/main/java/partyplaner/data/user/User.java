package partyplaner.data.user;

import android.media.Image;

/**
 * Created by André on 24.11.2017.
 */

public class User {

    private int id;
    private String email;
    private String name;
    private String birthdate;
    private int gender;
    private Image profilePicture;

    public User(int id, String email, String username,
                String birthday, int gender, Image profilePicture) {
        this.id = id;
        this.email = email;
        this.name = username;
        this.birthdate = birthday;
        this.gender = gender;
        this.profilePicture = profilePicture;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public int getGender() {
        return gender;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public static User createTestUser() {
        return new User(0, "tsm@fh-bielefeld.de", "henkershelfer",
                "17.7.1954", 1, null);
    }
}
