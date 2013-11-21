package com.alexkorovyansky.twitterchampions.app.config;

import android.app.Application;

/**
 * Configurator
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public abstract class Configurator {
    public abstract Config loadConfig(Application application);
    public abstract void saveConfig(Application application, Config config);
}
