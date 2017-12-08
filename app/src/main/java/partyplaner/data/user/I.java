package partyplaner.data.user;

import android.media.Image;

/**
 * Created by André on 08.12.2017.
 */

public class I extends User{

    private String key;

    private I(String email, String name, String birthdate, int gender, Image profilePicture, String apiKey) {
        super(email, name, birthdate, gender, profilePicture);
        this.key = apiKey;
    }

    public String getApiKey() {
        return key;
    }
}
