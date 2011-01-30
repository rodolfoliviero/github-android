package com.github;

public class Repository {

    private String owner;
    private String name;

    public Repository(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    @Override
    public String toString() {
        return owner + "/" + name;
    }

    public String commitsUrl() {
        return "http://github.com/api/v2/json/commits/list/" + owner + "/" + name + "/master";
    }
}