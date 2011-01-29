package com.github;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Feed {

    private String author;
    private String message;
    private String date;
    private Bitmap gravatar;

    public Feed(String author, String message, String date, String gravatarID) {
        this.author = author;
        this.message = message;
        this.date = date;
        decodeGravatar(gravatarID);
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getGravatar() {
        return gravatar;
    }

    private void decodeGravatar(String gravatarID) {
        try {
            URL url = new URL("http://www.gravatar.com/avatar/" + gravatarID);
            InputStream stream = url.openStream();
            gravatar = BitmapFactory.decodeStream(stream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}