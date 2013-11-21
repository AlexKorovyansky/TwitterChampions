package com.alexkorovyansky.twitterchampions.app.components.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.services.app.model.UserWithTweets;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

/**
 * ChampionsListAdapter
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ChampionsListAdapter extends ArrayAdapter<UserWithTweets> {

    public static class ViewHolder {
        @InjectView(R.id.champions_list_item_avatar) ImageView avatarImageView;
        @InjectView(R.id.champions_list_item_name) TextView nameTextView;
        @InjectView(R.id.champions_list_item_login) TextView loginTextView;
        @InjectView(R.id.champions_list_item_tweets_counter) TextView counterTextView;
        @InjectView(R.id.champions_list_item_tweets_highlight) TextView tweetsHighlightTextView;

        public static void fillHolder(Context context, ViewHolder viewHolder, UserWithTweets userWithTweets) {
            Picasso.with(context).load(userWithTweets.getUser().getAvatarUrl()).into(viewHolder.avatarImageView);
            viewHolder.nameTextView.setText(userWithTweets.getUser().getName());
            viewHolder.loginTextView.setText("@" + userWithTweets.getUser().getLogin());
            viewHolder.counterTextView.setText("" + userWithTweets.getTweets().size());
            viewHolder.tweetsHighlightTextView.setText(userWithTweets.getUser().getAbout());
        }
    }

    private LayoutInflater layoutInflater;

    public ChampionsListAdapter(Context context, List<UserWithTweets> data) {
        super(context, R.layout.layout_champions_list_item, -1, data);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.layout_champions_list_item, parent, false);
            viewHolder = new ViewHolder();
            Views.inject(viewHolder, view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        final UserWithTweets userWithTweets = getItem(position);
        ViewHolder.fillHolder(getContext(), viewHolder, userWithTweets);

        return view;
    }

}
