package partyplaner.data.party;

/**
 * Created by malte on 09.01.2018.
 */

public class Todo {
    private String text;
    private int status;

    public Todo(String text, int status) {
        this.text = text;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public int getStatus() {
        return status;
    }
}
