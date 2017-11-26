package partyplaner.data.party;

import java.util.GregorianCalendar;
import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Comment {

    private User commentator;
    private String commentContent;
    private GregorianCalendar commentDateAndTime;
    private List<Comment> answers;

    public Comment(User commentator, String commentContent, GregorianCalendar commentDateAndTime, List<Comment> answers) {
        this.commentator = commentator;
        this.commentContent = commentContent;
        this.commentDateAndTime = commentDateAndTime;
        this.answers = answers;
    }

    public User getCommentator() {
        return commentator;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public GregorianCalendar getCommentDateAndTime() {
        return commentDateAndTime;
    }

    public List<Comment> getAnswers() {
        return  answers;
    }
}
