package partyplaner.data.party;

import java.io.Serializable;
import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class PollOption implements Serializable{

    private String name;
    private int votedUsers;

    public PollOption(String name, int votedUsers) {
        this.name= name;
        this.votedUsers = votedUsers;
    }

    public String getName() {
        return name;
    }

    public int getVotedUsers() {
        return votedUsers;
    }

    public boolean hasVoted(User user) {
        //TODO Datenbank abfragen ob user Abgestimmt hat
        return false;
    }
}
