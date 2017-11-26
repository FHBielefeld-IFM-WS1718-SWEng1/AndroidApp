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

    public Guest(String email, String name, String surname, String password,
                GregorianCalendar birthday, String gender, String address, Image profilePicture,
                 InviteState inviteState, boolean admin) {
        super(email, name, surname, password, birthday, gender, address, profilePicture);
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
