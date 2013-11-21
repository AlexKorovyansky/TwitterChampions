package com.alexkorovyansky.twitterchampions.services.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UserWithTweets
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class UserWithTweets implements Parcelable {
    private final User user;
    private final List<Tweet> tweets;

    public static Parcelable.Creator<UserWithTweets> CREATOR = new Parcelable.Creator<UserWithTweets>() {
        public UserWithTweets createFromParcel(Parcel source) {
            return new UserWithTweets(source);
        }

        public UserWithTweets[] newArray(int size) {
            return new UserWithTweets[size];
        }
    };

    public UserWithTweets(User user, List<Tweet> tweets) {
        this.user = user;
        this.tweets = Collections.unmodifiableList(tweets);
    }

    private UserWithTweets(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.tweets = new ArrayList<Tweet>();
        in.readList(this.tweets, Tweet.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "UserWithTweets{" +
                "user=" + user +
                ", tweets=" + tweets +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, flags);
        dest.writeList(this.tweets);
    }

    public User getUser() {
        return user;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

}
