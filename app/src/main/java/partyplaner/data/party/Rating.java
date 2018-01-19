package partyplaner.data.party;

import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Rating {

    private int rating;
    private int user_id;
    private int party_id;

    public Rating(int rating, List<User> ratedUsers) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getParty_id() {
        return party_id;
    }


}
