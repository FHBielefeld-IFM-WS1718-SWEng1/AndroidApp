package partyplaner.data.party;

import java.io.Serializable;
import java.util.List;

import partyplaner.data.user.I;
import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class PollOption implements Serializable{

    private int id;
    private String text;
    private int votes;
    private UserChoices[] userChoices;

    public PollOption(String text, int votes) {
        this.text = text;
        this.votes = votes;
    }

    public String getText() {
        return text;
    }

    public int getVotes() {
        return votes;
    }

    public UserChoices[] getUserChoices() {
        return userChoices;
    }

    public boolean isMyChoice() {
        for (UserChoices userChoice : userChoices) {
            if (userChoice.getUser().getId() == I.getMyself().getId()) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void addVote() {
        votes++;
        UserChoices[] placeholder = userChoices;
        userChoices = new UserChoices[placeholder.length + 1];
        for (int i = 0; i < placeholder.length; i++) {
            userChoices[i] = placeholder[i];
        }
        userChoices[placeholder.length] = new UserChoices();
    }
}
