package com.alexkorovyansky.twitterchampions.app.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.alexkorovyansky.twitterchampions.app.TwitterChampionsApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * TwitterChampionsActivity
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class TwitterChampionsActivity extends FragmentActivity {

    @Inject Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterChampionsApplication.get(this).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    protected void postToBus(Object object) {
        bus.post(object);
    }

}
