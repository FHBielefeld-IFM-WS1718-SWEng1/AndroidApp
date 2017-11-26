package partyplaner.data.party;

import java.util.ArrayList;
import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Poll {

    private String question;
    private List<PollOption> pollOptions;

    public Poll(String question, List<PollOption> pollOptions) {
        this.question = question;
        if((this.pollOptions = pollOptions) == null)
            this.pollOptions = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public List<PollOption> getPollOptions() {
        return pollOptions;
    }

    public boolean hasVoted(User user) {
        return hasVotedFor(user) != null;
    }

    public PollOption hasVotedFor(User user) {
        for (PollOption current : pollOptions)
            if(current.hasVoted(user))
                return current;
        return null;
    }

    public List<String> getOptionTitles() {
        List<String> optionTitles = new ArrayList<>();
        for (PollOption current : pollOptions)
            optionTitles.add(current.getName());
        return optionTitles;
    }

    public List<User> getVotedUsers() {
        List<User> votedUsers = new ArrayList<>();
        for (PollOption current : pollOptions)
            votedUsers.addAll(current.getVotedUsers());
        return votedUsers;
    }

    public int getVoteCount() {
        int count = 0;
        for (PollOption current : pollOptions)
            count += current.getVoteCount();
        return count;
    }
}
