package partyplaner.data.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Poll implements Serializable {

    private int id;
    private String name;
    private PollOption[] choices;

    public Poll(String question, PollOption[] choices) {
        this.name = question;
        this.choices = choices;
    }

    public String getQuestion() {
        return name;
    }

    public PollOption[] getChoices() {
        return choices;
    }

    public int getId() {
        return id;
    }

}
