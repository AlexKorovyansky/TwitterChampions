package com.alexkorovyansky.twitterchampions.app.components.ui.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.app.base.TwitterChampionsListFragment;
import com.alexkorovyansky.twitterchampions.app.components.ui.adapters.ChampionsListAdapter;
import com.alexkorovyansky.twitterchampions.app.components.ui.events.ChampionSelectedEvent;
import com.alexkorovyansky.twitterchampions.app.config.Config;
import com.alexkorovyansky.twitterchampions.services.app.RealTwitterChampionsService;
import com.alexkorovyansky.twitterchampions.services.app.model.UserWithTweets;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * ChampionsListFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ChampionsListFragment extends TwitterChampionsListFragment {

    @Inject RealTwitterChampionsService twitterChampionsService;
    @Inject Config config;

    private ArrayList<UserWithTweets> lastResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initFromBundle(savedInstanceState);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getListAdapter() == null) {
            if (lastResult != null) {
                setListAdapter(new ChampionsListAdapter(getActivity(), lastResult));
            } else {
                doSearch();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.champions_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            doSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final UserWithTweets userWithTweets = (UserWithTweets) getListAdapter().getItem(position);
        postToBus(new ChampionSelectedEvent(userWithTweets));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (lastResult != null) {
            outState.putParcelableArrayList("lastResult", lastResult);
        }
    }

    private void initFromBundle(Bundle bundle) {
        lastResult = bundle.getParcelableArrayList("lastResult");
    }

    private void doSearch() {
        setListShown(false);
        twitterChampionsService.searchChampions(config.getHashTags(), new RealTwitterChampionsService.Callback() {
            @Override
            public void onResult(List<UserWithTweets> result) {
                Timber.d("onResult", result);
                if (getActivity() != null) {
                    lastResult = new ArrayList<UserWithTweets>(result);
                    setListAdapter(new ChampionsListAdapter(getActivity(), result));
                    setListShown(true);
                }
            }

            @Override
            public void onError(Exception e) {
                Timber.d(e, "onError");
                if (getActivity() != null) {
                    setEmptyText("error " + e.getMessage());
                    setListAdapter(null);
                    setListShown(true);
                }
            }
        });
    }


}
