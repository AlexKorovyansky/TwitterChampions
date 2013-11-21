package com.alexkorovyansky.twitterchampions.app.components.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.services.app.model.Tweet;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

/**
 * ChampionsListAdapter
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ChampionDetailsListAdapter extends ArrayAdapter<Tweet> {

    class ViewHolder {
        @InjectView(R.id.champions_details_list_item_time) TextView timeTextView;
        @InjectView(R.id.champions_details_list_item_tweet) TextView tweetTextView;
    }

    private LayoutInflater layoutInflater;
    private SimpleDateFormat timestampDateFormat = new SimpleDateFormat("MMM dd, HH:mm");

    public ChampionDetailsListAdapter(Context context, List<Tweet> data) {
        super(context, -1, data);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.layout_champion_details_list_item, parent, false);
            viewHolder = new ViewHolder();
            Views.inject(viewHolder, view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        final Tweet tweet = getItem(position);
        viewHolder.timeTextView.setText(timestampDateFormat.format(tweet.getTimestamp()));
        viewHolder.tweetTextView.setText(tweet.getText());

        return view;
    }

}
