package partyplaner.data.user;

import android.media.Image;

/**
 * Die I Klasse erbt von der User Klasse und enhält so alle Informationen über den derzeitig
 * eingeloggten Benutzer sowie zusätzlich dessen ApiKey.
 *
 * @author André
 * @since 08.12.2017
 */

public class I extends User{

    private String key;

    private I(int id, String email, String name, String birthdate, int gender, Image profilePicture, String apiKey) {
        super(id, email, name, birthdate, gender, profilePicture);
        this.key = apiKey;
    }

    public String getApiKey() {
        return key;
    }
}
