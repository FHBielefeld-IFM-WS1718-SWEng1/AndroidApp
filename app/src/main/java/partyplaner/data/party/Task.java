package partyplaner.data.party;

import partyplaner.data.user.User;

/**
 * Created by André on 26.11.2017.
 */

public class Task {

    private String text;
    private User user;
    private int status;

    public Task(String title, User responsibleUser, int status) {
        this.text = title;
        this.user = responsibleUser;
        this.status = status;
    }

    public String getTask() {
        return text;
    }

    public User getResponsibleUser() {
        return user;
    }

    public int isDone() {
        return status;
    }
}
