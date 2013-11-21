package com.alexkorovyansky.twitterchampions.app.components.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alexkorovyansky.twitterchampions.R;
import com.alexkorovyansky.twitterchampions.app.TwitterChampionsApplication;
import com.alexkorovyansky.twitterchampions.app.base.TwitterChampionsFragment;
import com.alexkorovyansky.twitterchampions.app.config.Config;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * ConfigFragment
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class ConfigFragment extends TwitterChampionsFragment {

    @InjectView(R.id.config_hash_tags) EditText hashTagsEdit;
    @InjectView(R.id.config_mock_twitter_service_checkbox) CheckBox mockTwitterServiceCheckBox;
    @InjectView(R.id.config_mock_write_logs_checkbox) CheckBox writeLogsCheckBox;
    @InjectView(R.id.config_mock_enable_crashlytics_checkbox) CheckBox enableCrashlyticsCheckBox;

    @Inject Config config;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hashTagsEdit.setText(config.getHashTagsAsString());
        mockTwitterServiceCheckBox.setChecked(config.isTwitterUseMock());
        writeLogsCheckBox.setChecked(config.isWriteLogs());
        enableCrashlyticsCheckBox.setChecked(config.isEnableCrashlytics());
    }

    @SuppressWarnings("UnusedDeclaration") //Used by injector
    @OnClick(R.id.config_apply_button)
    /*injected*/ void apply() {
        final TwitterChampionsApplication app = TwitterChampionsApplication.get(this);
        final String[] hashTags = parseHashTags();
        if (hashTags.length == 0) {
            Toast.makeText(getActivity(), "cannot parse hashtags", Toast.LENGTH_LONG).show();
        } else {
            final Config config = new Config.Builder()
                    .twitterUseMock(mockTwitterServiceCheckBox.isChecked())
                    .writeLogs(writeLogsCheckBox.isChecked())
                    .enableCrashlytics(enableCrashlyticsCheckBox.isChecked())
                    .hashTags(hashTags)
                    .build();
            app.resetConfig(config);
        }
    }

    private String[] parseHashTags() {
        final String[] hashTags = hashTagsEdit.getText().toString().trim().split("\\s+");
        return hashTags;
    }
}
