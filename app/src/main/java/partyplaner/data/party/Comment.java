package partyplaner.data.party;

import java.util.GregorianCalendar;
import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Comment {

    private int id;
    private int user_id;
    private int party_id;
    private String text;
    private User user;
    private Comment[] Answer;

    public Comment(int id, int user_id, int party_id, String text, partyplaner.data.user.User user, Comment[] answer) {
        this.id = id;
        this.user_id = user_id;
        this.party_id = party_id;
        this.text = text;
        user = user;
        Answer = answer;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getParty_id() {
        return party_id;
    }

    public String getText() {
        return text;
    }

    public partyplaner.data.user.User getUser() {
        return user;
    }

    public Comment[] getAnswer() {
        return Answer;
    }

    public void setUser(partyplaner.data.user.User user) {
        user = user;
    }
}
