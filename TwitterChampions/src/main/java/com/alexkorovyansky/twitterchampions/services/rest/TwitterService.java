package com.alexkorovyansky.twitterchampions.services.rest;

import com.alexkorovyansky.twitterchampions.services.rest.model.OAuthResult;
import com.alexkorovyansky.twitterchampions.services.rest.model.SearchResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * TwitterService
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public interface TwitterService {

    @FormUrlEncoded
    @POST("/oauth2/token")
    public void oauth2(@Header("Authorization") String auth, @Field("grant_type") String grantType, Callback<OAuthResult> callback);

    @GET("/1.1/search/tweets.json")
    public void search(@Header("Authorization") String auth,
                       @Query("q") String query, @Query("count") int count, @Query("result_type") String resultType,
                       Callback<SearchResult> callback);
}
