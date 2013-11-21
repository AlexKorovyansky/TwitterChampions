package com.alexkorovyansky.twitterchampions.app.modules;

import android.os.Handler;

import com.alexkorovyansky.twitterchampions.app.components.ui.activities.ChampionsActivity;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionsListFragment;
import com.alexkorovyansky.twitterchampions.services.rest.MockTwitterService;
import com.alexkorovyansky.twitterchampions.services.rest.TwitterService;

import java.util.Random;

import dagger.Module;
import dagger.Provides;

/**
 * MockServiceModule
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Module(injects = {ChampionsActivity.class, ChampionsListFragment.class})
public class MockServiceModule {

    @Provides
    public TwitterService provideTwitterService(Handler handler, Random random) {
        return new MockTwitterService(handler, random);
    }
}
