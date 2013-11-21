package com.alexkorovyansky.twitterchampions.app.components.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alexkorovyansky.twitterchampions.BuildConfig;
import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.app.base.TwitterChampionsActivity;
import com.alexkorovyansky.twitterchampions.app.components.ui.events.ChampionSelectedEvent;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionDetailsListFragment;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ChampionsListFragment;
import com.alexkorovyansky.twitterchampions.app.config.Config;
import com.crashlytics.android.Crashlytics;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


public class ChampionsActivity extends TwitterChampionsActivity {

    @Inject Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (config.isEnableCrashlytics()) {
            Crashlytics.start(this);
        }
        setTitle(config.getHashTagsAsString());
        setContentView(R.layout.layout_container);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_placeholder, new ChampionsListFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_site) {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(BuildConfig.SITE));
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_dev_config) {
            final Intent intent = new Intent(this, ConfigActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("UnusedDeclaration") //Used by event bus
    @Subscribe
    public void onChampionSelected(ChampionSelectedEvent event) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_placeholder, ChampionDetailsListFragment.newInstance(event.getUserWithTweets()), "aa")
                .addToBackStack("champion_details")
                .commit();
    }
    
}
