package com.alexkorovyansky.twitterchampions.app.components.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.app.base.TwitterChampionsListFragment;
import com.alexkorovyansky.twitterchampions.app.components.ui.adapters.ChampionDetailsListAdapter;
import com.alexkorovyansky.twitterchampions.app.components.ui.adapters.ChampionsListAdapter;
import com.alexkorovyansky.twitterchampions.services.app.model.Tweet;
import com.alexkorovyansky.twitterchampions.services.app.model.UserWithTweets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Views;

/**
 * ChampionsListFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ChampionDetailsListFragment extends TwitterChampionsListFragment {

    private UserWithTweets userWithTweets;

    public static ChampionDetailsListFragment newInstance(UserWithTweets userWithTweets) {
        final ChampionDetailsListFragment championDetailsListFragment = new ChampionDetailsListFragment();
        final Bundle arguments = new Bundle();
        final List<Tweet> sortedTweets = new ArrayList<Tweet>(userWithTweets.getTweets());
        Collections.sort(sortedTweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet lhs, Tweet rhs) {
                return (int) (rhs.getTimestamp().getTime() - lhs.getTimestamp().getTime());
            }
        });
        arguments.putParcelable("userWithTweets", new UserWithTweets(userWithTweets.getUser(), sortedTweets));
        championDetailsListFragment.setArguments(arguments);
        return championDetailsListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFromBundle(savedInstanceState == null ? getArguments() : savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View headerView = inflater.inflate(R.layout.layout_champions_list_item, null);
        final ChampionsListAdapter.ViewHolder viewHolder = new ChampionsListAdapter.ViewHolder();
        Views.inject(viewHolder, headerView);
        ChampionsListAdapter.ViewHolder.fillHolder(getActivity(), viewHolder, userWithTweets);
        getListView().addHeaderView(headerView);
        setListAdapter(new ChampionDetailsListAdapter(getActivity(), userWithTweets.getTweets()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("userWithTweets", userWithTweets);
    }

    private void initFromBundle(Bundle bundle) {
        userWithTweets = bundle.getParcelable("userWithTweets");
    }


}
