package com.github;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.github.HttpRequest.get;

public class GithubService {

    public List<Repository> searchRepositories() {
        List<Repository> repositories = new ArrayList<Repository>();
        try {
            String repositoriesUrl = "http://github.com/api/v2/json/repos/watched/rodolfoliviero";
            JSONArray repositoriesJson = get(repositoriesUrl).getJSONArray("repositories");

            for (int i = 0; i < 5; i++) {
                String name = repositoriesJson.getJSONObject(i).getString("name");
                String owner = repositoriesJson.getJSONObject(i).getString("owner");
                Repository repo = new Repository(owner, name);
                repositories.add(repo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return repositories;
    }

    public List<Feed> searchFeeds() {
        List<Feed> feeds = new ArrayList<Feed>();

        for (Repository repository : searchRepositories()) {

            try {
                JSONArray commits = get(repository.commitsUrl()).getJSONArray("commits");
                for (int j = 0; j < commits.length(); j++) {
                    if (j > 0) {
                        break;
                    }

                    String message = commits.getJSONObject(j).getString("message");
                    String date = commits.getJSONObject(j).getString("committed_date");
                    String author = commits.getJSONObject(j).getJSONObject("committer").getString("login");

                    Feed feed = new Feed(author, message, date, getGravatarID(author));
                    feeds.add(feed);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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