package com.github;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GithubActivity extends Activity {

    private ListView feeds;
    private final List<View> viewRows = new ArrayList<View>();
    private final GithubService githubService = new GithubService();
    private final Timer timer = new Timer();
    private Date lastSearchDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        feeds = (ListView) findViewById(R.id.feeds);
        synchronizeFeeds();
    }

    private void synchronizeFeeds() {
        Toast.makeText(this, "retrieving feeds", Toast.LENGTH_LONG).show();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    if (lastSearchDate == null) {
                        lastSearchDate = sdf.parse("2011-01-25T01:01:01");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                final List<Feed> feeds = githubService.searchFeeds(lastSearchDate);
                lastSearchDate = new Date();
                final List<View> newViews = createListView(feeds);

                runOnUiThread(new Runnable() {
                    public void run() {
                        fillScreen(newViews);
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private List<View> createListView(List<Feed> feeds) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        List<View> listView = new ArrayList<View>();

        for (Feed feed : feeds) {
            View view = layoutInflater.inflate(R.layout.feed, null);
            TextView event = (TextView) view.findViewById(R.id.event);
            TextView message = (TextView) view.findViewById(R.id.message);
            ImageView gravatar = (ImageView) view.findViewById(R.id.gravatar);

            event.setText(feed.getAuthor() + " pushed to master at " + feed.getRepository() + " " + feed.getDate());
            message.setText(feed.getMessage());
            gravatar.setImageBitmap(feed.getGravatar());

            listView.add(view);
        }

        return listView;
    }

    private void fillScreen(final List<View> newViews) {
        Toast.makeText(this, newViews.size() + " new feeds", Toast.LENGTH_SHORT).show();
        viewRows.addAll(0, newViews);
        feeds.setAdapter(new ArrayAdapter<View>(this, android.R.layout.simple_list_item_1, viewRows) {
            public View getView(int position, View convertView, ViewGroup parent) {
                return viewRows.get(position);
            }
        });
    }
}