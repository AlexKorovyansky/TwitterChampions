package com.alexkorovyansky.twitterchampions.app.components.ui.events;

import com.alexkorovyansky.twitterchampions.services.app.model.UserWithTweets;

/**
 * ChampionSelectedEvent
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ChampionSelectedEvent {

    private final UserWithTweets userWithTweets;

    public ChampionSelectedEvent(UserWithTweets userWithTweets) {
        this.userWithTweets = userWithTweets;
    }

    public UserWithTweets getUserWithTweets() {
        return userWithTweets;
    }
}
