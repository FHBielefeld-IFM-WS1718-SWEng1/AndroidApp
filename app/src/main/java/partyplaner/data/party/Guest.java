package partyplaner.data.party;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class Guest{

    private int id;
    private int status;

    //TODO: manuelles Parsen
    private User user;

    public Guest(int status, User user) {
        this.status = status;
        this.user = user;
    }

    public int getInviteState() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public int getId() {
        return id;
    }
}
