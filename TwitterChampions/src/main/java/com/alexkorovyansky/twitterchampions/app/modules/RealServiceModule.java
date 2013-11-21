package com.alexkorovyansky.twitterchampions.app.modules;

import com.alexkorovyansky.twitterchampions.app.components.ui.activities.ChampionsActivity;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionDetailsListFragment;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionsListFragment;
import com.alexkorovyansky.twitterchampions.app.config.Config;
import com.alexkorovyansky.twitterchampions.services.rest.TwitterService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * RealServiceModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(injects = {ChampionsActivity.class, ChampionsListFragment.class, ChampionDetailsListFragment.class})
public class RealServiceModule {

    public static final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZ yyyy";

    private Config config;

    public RealServiceModule(Config config) {
        this.config = config;
    }

    @Provides
    public TwitterService provideTwitterService() {

        final JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                try {
                    final SimpleDateFormat sf = new SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.ENGLISH);
                    return sf.parse(json.getAsString());
                } catch (ParseException e) {
                    throw new JsonParseException("cannot parse date", e);
                }
            }
        };

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateJsonDeserializer).create();

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer("https://api.twitter.com")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(config.isWriteLogs() ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        return restAdapter.create(TwitterService.class);
    }

}
