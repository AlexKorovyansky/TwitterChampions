package com.alexkorovyansky.twitterchampions.services.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class User implements Parcelable {
    private final String login;
    private final String name;
    private final String avatarUrl;
    private final String about;

    public static Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        this.login = in.readString();
        this.name = in.readString();
        this.avatarUrl = in.readString();
        this.about = in.readString();
    }

    public User(String login, String name, String avatarUrl, String about) {
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.about = about;
    }

    public User(User user) {
        this.login = user.login;
        this.name = user.name;
        this.avatarUrl = user.avatarUrl;
        this.about = user.about;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User user = (User) o;
            if (login != null ? !login.equals(user.login) : user.login != null) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.name);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.about);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAbout() {
        return about;
    }


}
