package partyplaner.data.party;

import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

class PollOption {

    private String name;
    private List<User> votedUsers;

    public PollOption(String name, List<User> votedUsers) {
        this.name= name;
        this.votedUsers = votedUsers;
    }

    public String getName() {
        return name;
    }

    public List<User> getVotedUsers() {
        return votedUsers;
    }

    public boolean hasVoted(User user) {
        return votedUsers.contains(user);
    }

    public int getVoteCount() {
        return votedUsers.size();
    }
}
