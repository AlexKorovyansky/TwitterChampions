package com.alexkorovyansky.twitterchampions.app.config;

import android.app.Application;

import com.alexkorovyansky.twitterchampions.BuildConfig;

/**
 * AppConfigurator
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class AppConfigurator extends Configurator {

    @Override
    public Config loadConfig(Application application) {
        return new Config.Builder()
                .twitterUseMock(false)
                .writeLogs(false)
                .enableCrashlytics(true)
                .build();
    }

    @Override
    public void saveConfig(Application application, Config config) {
        // do nothing
    }

}
