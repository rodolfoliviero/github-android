package com.github.model;

import com.github.util.Utils;

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
        return Utils.URL_COMMITS + owner + "/" + name + "/master";
    }
}