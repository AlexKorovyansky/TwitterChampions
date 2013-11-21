package com.alexkorovyansky.twitterchampions.services.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * SearchResult
 *
 * @author Alex Korovyansky <korovyansk@gmail.com.com>
 */
public class SearchResult {

    @SerializedName("statuses")
    public List<Status> statuses;

    public static class Status {

        @SerializedName("id")
        public long id;

        @SerializedName("created_at")
        public Date createdAt;

        @SerializedName("text")
        public String text;

        @SerializedName("user")
        public User user;

    }

    public static class User {
        @SerializedName("id")
        public long id;

        @SerializedName("name")
        public String name;

        @SerializedName("screen_name")
        public String nickName;

        @SerializedName("profile_image_url")
        public String url;

        @SerializedName("description")
        public String description;

    }

}
