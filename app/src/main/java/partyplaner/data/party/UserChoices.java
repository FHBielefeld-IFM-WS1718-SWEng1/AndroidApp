package partyplaner.data.party;

import java.io.Serializable;

import partyplaner.data.user.User;

/**
 * Created by malte on 22.01.2018.
 */

public class UserChoices implements Serializable {
    private int id;
    private User user;

    public User getUser() {
        return user;
    }
}
