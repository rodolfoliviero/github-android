package com.github;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GithubActivity extends Activity {

    private ListView feeds;
    private final List<View> viewRows = new ArrayList<View>();
    private final GithubService githubService = new GithubService();
    private final Timer timer = new Timer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        feeds = (ListView) findViewById(R.id.feeds);
        synchronizeFeeds();
    }

    private void synchronizeFeeds() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                final List<Feed> feeds = githubService.searchFeeds();
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
            TextView message = (TextView) view.findViewById(R.id.message);
            ImageView gravatar = (ImageView) view.findViewById(R.id.gravatar);

            message.setText(feed.getAuthor() + " " + feed.getMessage() + " " + feed.getDate());
            gravatar.setImageBitmap(feed.getGravatar());

            listView.add(view);
        }

        return listView;
    }

    private void fillScreen(final List<View> newViews) {
        viewRows.addAll(0, newViews);
        feeds.setAdapter(new ArrayAdapter<View>(this, android.R.layout.simple_list_item_1, viewRows) {
            public View getView(int position, View convertView, ViewGroup parent) {
                return viewRows.get(position);
            }
        });
    }
}