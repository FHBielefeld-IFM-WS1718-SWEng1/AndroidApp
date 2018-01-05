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

    private String name;
    private String description;
    private String startDate;
    private String enddate;
    private String location;
    private int user_id;
    private boolean ersteller;

    private User organizer;
    private GregorianCalendar dateAndTime;

    private List<Image> gallery;

    //TODO: ToDo-/Kostenliste
    private List<Guest> guests;
    private List<Comment> comments;
    private Rating rating;
    private List<Poll> polls;
    private List<Task> tasks;

    public Party(String name, String description, User organizer, String location,
                 GregorianCalendar dateAndTime, List<Image> gallery, List<Guest> guests,
                 List<Comment> comments, Rating rating, List<Poll> polls, List<Task> tasks) {
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

    public List<Guest> getGuests() {
        return guests;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Rating getRating() {
        return rating;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Poll> getPolls() {
        return polls;
    }
}
