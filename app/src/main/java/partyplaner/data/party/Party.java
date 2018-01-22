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
    private String picture;

    private User organizer;
    private GregorianCalendar dateAndTime;

    private List<Image> gallery;

    //TODOFragment: ToDo-/Kostenliste
    private List<Comment> comments;
    private Rating[] rating;
    private int ratingAverage;
    private Poll[] polls;
    private Guest[] guests;
    private Task[] tasks;
    private Todo[] todo;

    public Party(String name, String description, User organizer, String location,
                 GregorianCalendar dateAndTime, List<Image> gallery, Guest[] guests,
                 List<Comment> comments, Rating[] rating, Poll[] polls, Task[] tasks, String picture) {
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
        this.picture = picture;
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

    public Rating[] getRating() {
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

    public Todo[] getTodo() {
        return todo;
    }

    static public String parseDate(String when) {
        String[] timeDate = when.split("T");
        String[] date = timeDate[0].split("-");

        return date[2] + "." + date[1] + "." + date[0] + ", " + timeDate[1].substring(0, 5) + "Uhr";
    }

    public int[] getStartDateArray() {
        String[] timeDate = this.startDate.split("T");
        String[] date = timeDate[0].split("-");
        String[] time = timeDate[1].replace("Z", "").split(":");
        int[] dateData = new int[5];
        dateData[0] = Integer.parseInt(date[0]);
        dateData[1] = Integer.parseInt(date[1]);
        dateData[2] = Integer.parseInt(date[2]);
        dateData[3] = Integer.parseInt(time[0]);
        dateData[4] = Integer.parseInt(time[1]);
        return dateData;
    }

    public int getAverageRating() {
        return ratingAverage;
    }

    public String getPicture() {
        return picture;
    }
}
