package com.alexkorovyansky.twitterchampions.services.app;

import com.alexkorovyansky.twitterchampions.services.app.model.Tweet;
import com.alexkorovyansky.twitterchampions.services.app.model.User;
import com.alexkorovyansky.twitterchampions.services.app.model.UserWithTweets;
import com.alexkorovyansky.twitterchampions.services.rest.TwitterService;
import com.alexkorovyansky.twitterchampions.services.rest.model.SearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * RealTwitterChampionsService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class RealTwitterChampionsService {

    public static abstract class Callback {
        public abstract void onResult(List<UserWithTweets> result);
        public abstract void onError(Exception e);
    }
    private final TwitterService twitterService;

    @Inject
    public RealTwitterChampionsService(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public void searchChampions(String[] hashtags, final Callback callback) {
        final String query = makeQuery(hashtags);
        twitterService.search("Bearer AAAAAAAAAAAAAAAAAAAAAGbCTgAAAAAA0nZGvSr79A1l%2FJfpphSh1wRm2tI%3DzzgcLxPjccQJSh3UUKIgx0NAtVV0WR1ThqdfnQJ7XQ",
                query, 100, "recent",
                new retrofit.Callback<SearchResult>() {
                    @Override
                    public void success(SearchResult searchResult, Response response) {
                        final Map<User, List<Tweet>> helpMap = new HashMap<User, List<Tweet>>();
                        for (SearchResult.Status status: searchResult.statuses) {
                            final User user = new User(status.user.nickName, status.user.name, status.user.url.replace("_normal", ""), status.user.description);
                            final Tweet tweet = new Tweet(status.id, status.text, status.createdAt);
                            final List<Tweet> userTweets = helpMap.get(user);

                            if (userTweets == null) {
                                helpMap.put(user, new ArrayList<Tweet>(Arrays.asList(tweet)));
                            } else {
                                userTweets.add(tweet);
                            }
                        }

                        final List<UserWithTweets> result = new ArrayList<UserWithTweets>();
                        for (User user: helpMap.keySet()) {
                            final List<Tweet> tweets = helpMap.get(user);
                            result.add(new UserWithTweets(user, tweets));
                        }

                        Collections.sort(result, new Comparator<UserWithTweets>() {
                            @Override
                            public int compare(UserWithTweets userWithTweets1, UserWithTweets userWithTweets2) {
                                return userWithTweets2.getTweets().size() - userWithTweets1.getTweets().size();
                            }
                        });

                        callback.onResult(result);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        callback.onError(retrofitError);
                    }
                });
    }

    private String makeQuery(String[] hashtags) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hashtags.length - 1; ++i) {
            stringBuilder.append("%23");
            stringBuilder.append(hashtags[i]);
            stringBuilder.append("%20");
        }
        stringBuilder.append("%23");
        stringBuilder.append(hashtags[hashtags.length - 1]);
        stringBuilder.append("+exclude:retweets");
        return stringBuilder.toString();
    }
}
