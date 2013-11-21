package com.alexkorovyansky.twitterchampions.app.modules;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.alexkorovyansky.twitterchampions.app.components.ui.activities.ChampionsActivity;
import com.alexkorovyansky.twitterchampions.app.components.ui.activities.ConfigActivity;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionDetailsListFragment;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionsListFragment;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ConfigFragment;
import com.alexkorovyansky.twitterchampions.app.config.Config;
import com.squareup.otto.Bus;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * AppModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(injects = {
        ChampionsActivity.class, ChampionsListFragment.class, ChampionDetailsListFragment.class,
        ConfigActivity.class, ConfigFragment.class
})
public class AppModule {

    private Application application;
    private Config config;
    private Handler handler;

    public AppModule(Application application, Config config) {
        this.application = application;
        this.config = config;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Provides @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Provides
    public Context provideContext() {
        return application;
    }

    @Provides
    public Config provideConfig() {
        return config;
    }

    @Provides
    public Handler provideHandler() {
        return handler;
    }

    @Provides
    public Random provideRandom() {
        return new Random(System.currentTimeMillis());
    }
}
