package partyplaner.data.user;

import android.media.Image;

import partyplaner.api.APIConnectionHandler;
import partyplaner.api.LoginHandler;

/**
 * Die I Klasse erbt von der User Klasse und enhält so alle Informationen über den derzeitig
 * eingeloggten Benutzer sowie zusätzlich dessen ApiKey.
 *
 * @author André
 * @since 08.12.2017
 */

public class I extends User{

    private static I myself;

    private String key;

    private I(int id, String email, String name, String birthdate, int gender, String profilePicture, String apiKey) {
        super(id, email, name, birthdate, gender, profilePicture);
        this.key = apiKey;
    }

    public String getApiKey() {
        return key;
    }

    static void setMyself(I i) {
        myself = i;
    }

    public static I getMyself() {
        return myself;
    }

    public boolean logout() {
        return LoginHandler.logout();
    }
}
