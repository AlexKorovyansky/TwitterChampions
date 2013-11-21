package com.alexkorovyansky.twitterchampions.services.rest.model;

import com.google.gson.annotations.SerializedName;

/**
 * OAuthResult
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class OAuthResult {
    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("token_type")
    public String tokenType;
}
