package partyplaner.data.party;

import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Rating {

    private int positiveRating;
    private List<User> ratedUsers;

    public Rating(int positiveRating, List<User> ratedUsers) {
        this.positiveRating = positiveRating;
        this.ratedUsers = ratedUsers;
    }

    public int getPositiveRating() {
        return positiveRating;
    }

    public int getNegativeRating() {
        return ratedUsers.size() - positiveRating;
    }

    public int getVoteCount() {
        return ratedUsers.size();
    }

    public float getPerventageRating() {
        return positiveRating / getVoteCount();
    }

    public boolean hasRated(User user) {
        return ratedUsers.contains(user);
    }
}
