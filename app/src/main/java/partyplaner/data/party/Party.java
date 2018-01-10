package partyplaner.data.party;

import android.media.Image;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import partyplaner.data.user.User;

/**
 * Created by André on 25.11.2017.
 */

public class Party implements Serializable{

    private int id;
    private String name;
    private String description;
    private String startDate;
    private String enddate;
    private User owner;
    private String location;
    private int user_id;
    private boolean ersteller;

    private User organizer;
    private GregorianCalendar dateAndTime;

    private List<Image> gallery;

    //TODOFragment: ToDo-/Kostenliste
    private List<Comment> comments;
    private Rating rating;
    private Poll[] polls;
    private Guest[] guests;
    private Task[] tasks;
    private Todo[] todo;

    public Party(String name, String description, User organizer, String location,
                 GregorianCalendar dateAndTime, List<Image> gallery, Guest[] guests,
                 List<Comment> comments, Rating rating, Poll[] polls, Task[] tasks) {
        this.name = name;
        this.description = description;
        this.organizer = organizer;
        this.location = location;
        this.dateAndTime = dateAndTime;
        this.gallery = gallery;
        this.guests = guests;
        this.comments = comments;
        this.rating = rating;
        this.polls = polls;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getOrganizer() {
        return organizer;
    }

    public String getLocation() {
        return location;
    }

    public GregorianCalendar getDateAndTime() {
        return dateAndTime;
    }

    public List<Image> getGallery() {
        return gallery;
    }

    public Guest[] getGuests() {
        return guests;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Rating getRating() {
        return rating;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public Poll[] getPolls() {
        return polls;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getUserID() {
        return user_id;
    }

    public boolean isErsteller() {
        return ersteller;
    }

    public User getOwner() {
        return owner;
    }
}
