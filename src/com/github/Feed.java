package com.github;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Feed {

    private Repository repository;
    private String author;
    private String message;
    private String date;
    private Bitmap gravatar;

    public Feed(String author, String message, String date, String gravatarID, Repository repository) {
        this.author = author;
        this.message = message;
        this.date = date;
        this.repository = repository;
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

    public Repository getRepository() {
        return repository;
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