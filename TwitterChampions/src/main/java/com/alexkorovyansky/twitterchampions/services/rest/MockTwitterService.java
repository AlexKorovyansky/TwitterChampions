package com.alexkorovyansky.twitterchampions.services.rest;

import android.os.Handler;

import com.alexkorovyansky.twitterchampions.services.rest.model.OAuthResult;
import com.alexkorovyansky.twitterchampions.services.rest.model.SearchResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * MockTwitterService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class MockTwitterService implements TwitterService {

//    @Override
//    public List<UserWithTweets> getTopUsersByHashTags(int numberOfUsers, String... hashTags) {
//        final List<UserWithTweets> result = new ArrayList<UserWithTweets>();
//        final User korovyansk = new User();
//        korovyansk.setLogin("@korovyansk");
//        korovyansk.setName("Alex Korovyansky");
//        korovyansk.setAbout("Lorem Ipsum");
//        korovyansk.setAvatarUrl("https://si0.twimg.com/profile_images/3651954319/93064f8e2585095cb420e978f827886f.jpeg");
//        final List<Tweet> tweets = new ArrayList<Tweet>();
//        final Tweet tweet = new Tweet();
//        tweet.setId(1);
//        tweet.setText("Lorem ipsum");
//        tweet.setTimestamp(new Date());
//        tweets.add(tweet);
//        result.add(new UserWithTweets(korovyansk, tweets));
//        return result;
//    }

    private final Handler handler;
    private final Random random;

    public MockTwitterService(Handler handler, Random random) {
        this.handler = handler;
        this.random = random;
    }

    @Override
    public void oauth2(@Header("Authorization") String auth, @Field("grant_type") String grantType, Callback<OAuthResult> callback) {

    }

    @Override
    public void search(@Header("Authorization") String auth, @Query("q") String query, @Query("count") int count, @Query("result_type") String resultType, final Callback<SearchResult> callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final SearchResult stubResult = new SearchResult();
                stubResult.statuses = new ArrayList<SearchResult.Status>();
                for (int i = 0; i < 1000; ++i) {
                    stubResult.statuses.add(makeStubStatus());
                }
                callback.success(stubResult, null);
            }
        }, 2000);
    }

    private SearchResult.Status makeStubStatus() {
        final SearchResult.Status status = new SearchResult.Status();
        int userId = random.nextInt(30);
        status.createdAt = new Date(new Date().getTime() - random.nextInt(3600*1000*24*4));
        status.id = 1;
        status.text = "Stub Stub Stub";
        status.user = new SearchResult.User();
        status.user.description = "Stub Description";
        status.user.name = "Stub Name";
        status.user.nickName = "stubNick" + userId;
        status.user.url= "http://lh5.ggpht.com/JaL4QvajGnzJNWtOajq3DI0vCJrZ9nt6-PrVRyGT4qNXxyoJVF5bOmTqz7kjDkvN7Q";
        status.user.id = userId;
        return status;

    }

}
