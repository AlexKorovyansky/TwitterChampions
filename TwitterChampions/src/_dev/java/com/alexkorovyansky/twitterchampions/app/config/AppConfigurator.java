package com.alexkorovyansky.twitterchampions.app.config;

/**
 * AppConfigurator
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class AppConfigurator extends PersistentConfigurator {

    @Override
    protected Config buildDefaultConfig() {
        return new Config.Builder()
                .twitterUseMock(false)
                .writeLogs(false)
                .enableCrashlytics(true)
                .build();
    }
}
