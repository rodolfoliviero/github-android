package com.github;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.github.HttpRequest.get;

public class GithubService {

    public List<Repository> searchRepositories() {
        List<Repository> repositories = new ArrayList<Repository>();
        try {
            String repositoriesUrl = "http://github.com/api/v2/json/repos/watched/JoseRibeiro";
            JSONArray repositoriesJson = get(repositoriesUrl).getJSONArray("repositories");

            for (int i = 0; i < repositoriesJson.length(); i++) {
                String name = repositoriesJson.getJSONObject(i).getString("name");
                String owner = repositoriesJson.getJSONObject(i).getString("owner");
                Repository repository = new Repository(owner, name);
                repositories.add(repository);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return repositories;
    }

    public List<Feed> searchFeeds(Date lastSearchDate) {
        List<Feed> feeds = new ArrayList<Feed>();

        for (Repository repository : searchRepositories()) {

            try {
                JSONArray commits = get(repository.commitsUrl()).getJSONArray("commits");
                for (int j = 0; j < commits.length(); j++) {
                    JSONObject commit = commits.getJSONObject(j);
                    String message = commit.getString("message");
                    String committedDateString = commit.getString("committed_date");
                    String author = commit.getJSONObject("committer").getString("login");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    try {
                        Date committedDate = sdf.parse(committedDateString);
                        if (committedDate.after(lastSearchDate)) {
                            Feed feed = new Feed(author, message, committedDate, getGravatarID(author), repository);
                            feeds.add(feed);
                        } else {
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(feeds, new Comparator<Feed>() {
            public int compare(Feed feed, Feed otherFeed) {
                return feed.getDate().compareTo(otherFeed.getDate()) * -1;
            }
        });
        return feeds;
    }

    public String getGravatarID(String user) {
        try {
            return get("http://github.com/api/v2/json/user/show/" + user).getJSONObject("user").getString("gravatar_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}