package partyplaner.data.party;

import android.media.Image;

import java.util.GregorianCalendar;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class Guest extends User{

    private InviteState inviteState;
    private boolean admin;
    public Guest(int id, String email, String username,
                String birthday, int gender, Image profilePicture,
                 InviteState inviteState, boolean admin) {
        super(id, email, username, birthday, gender, profilePicture);
        this.inviteState = inviteState;
        this.admin = admin;
    }

    public InviteState getInviteState() {
        return inviteState;
    }

    public boolean isAdmin() {
        return admin;
    }
}
