package com.alexkorovyansky.twitterchampions.app.config;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * PersistentConfigurator
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public abstract class PersistentConfigurator extends Configurator {

    @Override
    public final Config loadConfig(Application application) {
        final SharedPreferences sharedPreferences = application.getSharedPreferences("devConfig", Context.MODE_PRIVATE);
        final String version = sharedPreferences.getString("version", "1.0.0");
        final String currentVersion = getCurrentVersion(application);
        if (currentVersion.equalsIgnoreCase(version)) {
            final String configJson = sharedPreferences.getString("configJSON", null);
            final Config config = Config.fromJSON(configJson);
            return config != null ? config : buildDefaultConfig();
        } else {
            return buildDefaultConfig();
        }
    }

    @Override
    public final void saveConfig(Application application, Config config) {
        final SharedPreferences sharedPreferences = application.getSharedPreferences("devConfig", Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString("configJSON", config.toJSON())
                .putString("version", getCurrentVersion(application))
                .commit();
    }

    private String getCurrentVersion(Application application) {
        try {
            final PackageInfo pInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("impossible to continue");
        }
    }

    protected abstract Config buildDefaultConfig();
}
