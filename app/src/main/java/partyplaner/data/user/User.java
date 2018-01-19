package partyplaner.data.user;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
/**
 * Ein User Objekt beinhaltet alle Informationen, die man über einen
 * Benutzer wissen sollte.
 *
 * @author André
 * @since 24.11.2017
 */

public class User {

    private int id;
    private String email;
    private String name;
    private String birthdate;
    private Integer gender;
    private String profilepicture;
    private Bitmap image;

    public User(int id, String email, String username,
                String birthday, int gender, String profilepicture) {
        this.id = id;
        this.email = email;
        this.name = username;
        this.birthdate = birthday;
        this.gender = gender;
        this.profilepicture = profilepicture;
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

    public String getProfilePicture() {
        return profilepicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setProfilePicture(String id) {
        this.profilepicture = id;
    }

    /**
     * Erstellt einen User, der für Tests verwendet werden kann, ohne mit der Server API Nutzerdaten
     * abzufragen.
     *
     * @return Testnutzer
     * @deprecated Wenn möglich, bitte einen Nutzer über die API abfragen
     */
    @Deprecated
    public static User createTestUser() {

        return new User(0, "tsm@fh-bielefeld.de", "henkershelfer",
                "17.7.1954", 1, "");
    }


    public static String formatDate(String date) {
        if (date != null) {
            Log.e("User", date);
            String[] part = date.split("-");
            return part[2] + "." + part[1] + "." + part[0];
        }
        return "";
    }

    public void setImage(String image) {
        byte[] decoded = Base64.decode(image, Base64.DEFAULT);
        this.image = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }

    public Bitmap getImage() {
        return image;
    }
}
