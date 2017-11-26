package partyplaner.data.party;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class Task {

    private String title;
    private User responsibleUser;
    private boolean done;

    public Task(String title, User responsibleUser, boolean done) {
        this.title = title;
        this.responsibleUser = responsibleUser;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public User getResponsibleUser() {
        return responsibleUser;
    }

    public boolean isDone() {
        return done;
    }
}
