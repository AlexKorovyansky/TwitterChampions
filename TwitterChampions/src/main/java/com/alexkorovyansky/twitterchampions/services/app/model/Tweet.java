package com.alexkorovyansky.twitterchampions.services.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Tweet
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class Tweet implements Parcelable {
    private final long id;
    private final String text;
    private final Date timestamp;

    public static Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };

    public Tweet(long id, String text, Date timestamp) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
    }


    private Tweet(Parcel in) {
        this.id = in.readLong();
        this.text = in.readString();
        this.timestamp = (Date) in.readSerializable();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tweet) {
            Tweet tweet = (Tweet) o;
            return tweet.id == id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.text);
        dest.writeSerializable(this.timestamp);
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
