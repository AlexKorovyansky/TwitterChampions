package com.alexkorovyansky.twitterchampions.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alexkorovyansky.twitterchampions.app.TwitterChampionsApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Views;

/**
 * TwitterChampionsFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class TwitterChampionsFragment extends Fragment {

    @Inject Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterChampionsApplication.get(this).inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Views.inject(this, view);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Views.reset(this);
    }

    protected void postToBus(Object object) {
        bus.post(object);
    }

}
