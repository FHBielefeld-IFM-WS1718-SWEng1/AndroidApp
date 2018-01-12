package partyplaner.data.party;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class Guest extends User{

    private int status;
    private boolean admin;
    public Guest(int id, String email, String username,
                String birthday, int gender, int profilePicture,
                 int status, boolean admin) {
        super(id, email, username, birthday, gender, profilePicture);
        this.status = status;
        this.admin = admin;
    }

    public int getInviteState() {
        return status;
    }

    public boolean isAdmin() {
        return admin;
    }
}
