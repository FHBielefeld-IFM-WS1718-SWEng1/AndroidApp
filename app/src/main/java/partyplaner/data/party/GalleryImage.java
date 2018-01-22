package partyplaner.data.party;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

import partyplaner.data.user.User;

/**
 * Created by malte on 22.01.2018.
 */

public class GalleryImage {

    private int id;
    private User uploader;
    private String text;
    private String file;

    public GalleryImage(int id, User uploader, String text, String file) {
        this.id = id;
        this.uploader = uploader;
        this.text = text;
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public User getUploader() {
        return uploader;
    }

    public String getText() {
        return text;
    }

    public String getFile() {
        return file;
    }
}
