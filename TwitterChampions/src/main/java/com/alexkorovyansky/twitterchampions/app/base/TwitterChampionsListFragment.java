package com.alexkorovyansky.twitterchampions.app.base;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexkorovyansky.twitterchampions.app.TwitterChampionsApplication;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.Views;

/**
 * TwitterChampionsListFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class TwitterChampionsListFragment extends ListFragment {

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

    protected View onCreateView(LayoutInflater inflater, ViewGroup container, int layoutId) {
        return inflater.inflate(layoutId, container, false);
    }

    protected void postToBus(Object object) {
        bus.post(object);
    }

}
