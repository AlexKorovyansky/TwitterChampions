package com.alexkorovyansky.twitterchampions.app.components.ui.activities;

import android.os.Bundle;

import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.app.base.TwitterChampionsActivity;
import com.alexkorovyansky.twitterchampions.app.components.ui.fragments.ConfigFragment;


public class ConfigActivity extends TwitterChampionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_container_scrollable);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_placeholder, new ConfigFragment())
                    .commit();
        }
    }

}
