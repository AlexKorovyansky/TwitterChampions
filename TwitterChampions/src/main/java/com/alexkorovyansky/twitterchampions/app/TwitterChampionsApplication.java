package com.alexkorovyansky.twitterchampions.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.alexkorovyansky.twitterchampions.BuildConfig;
import com.alexkorovyansky.twitterchampions.app.components.ui.activities.ChampionsActivity;
import com.alexkorovyansky.twitterchampions.app.config.AppConfigurator;
import com.alexkorovyansky.twitterchampions.app.config.Config;
import com.alexkorovyansky.twitterchampions.app.modules.AppModule;
import com.alexkorovyansky.twitterchampions.app.modules.MockServiceModule;
import com.alexkorovyansky.twitterchampions.app.modules.RealServiceModule;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * TwitterChampionsApplication
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class TwitterChampionsApplication extends Application {

    private ObjectGraph objectGraph;
    private Config config;

    private static class CrashlyticsTree implements Timber.Tree {

        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");

        private String createTag() {
            String tag = new Throwable().getStackTrace()[3].getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m != null && m.find()) {
                tag = m.replaceAll("");
            }
            return tag.substring(tag.lastIndexOf('.') + 1);
        }

        @Override
        public void d(String s, Object... objects) {
            // do nothing
        }

        @Override
        public void d(Throwable throwable, String s, Object... objects) {
            // do nothing
        }

        @Override
        public void i(String s, Object... objects) {
            Crashlytics.log(createTag() + " " + String.format(s, objects));
            try {
                if (objects.length == 2) {
                    if ("%s == %s".equalsIgnoreCase(s)) {
                        Crashlytics.setString((String) objects[0], objects[1] == null ? "null" : objects[1].toString());
                    } else if ("%s == %b".equalsIgnoreCase(s)) {
                        Crashlytics.setBool((String) objects[0], (Boolean) objects[1]);
                    } else if ("%s == %d".equalsIgnoreCase(s)) {
                        Crashlytics.setInt((String) objects[0], (Integer) objects[1]);
                    } else if ("%s == %f".equalsIgnoreCase(s)) {
                        Crashlytics.setDouble((String) objects[0], (Double) objects[1]);
                    } else if ("userIdentifier == %s".equalsIgnoreCase(s)) {
                        Crashlytics.setUserIdentifier((String)objects[0]);
                    }
                }
            } catch (Exception e) {
                i(e, "exception while writing custom key/user info");
            }
        }

        @Override
        public void i(Throwable throwable, String s, Object... objects) {
            Crashlytics.log(createTag() + " " + String.format(s, objects));
            Crashlytics.logException(throwable);
        }

        @Override
        public void w(String s, Object... objects) {
            i(s, objects);
        }

        @Override
        public void w(Throwable throwable, String s, Object... objects) {
            i(throwable, s, objects);
        }

        @Override
        public void e(String s, Object... objects) {
            i(s, objects);
        }

        @Override
        public void e(Throwable throwable, String s, Object... objects) {
            i(throwable, s, objects);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        config = new AppConfigurator().loadConfig(this);
        prepareLogs();
        prepareObjectGraph();
        Timber.i("--onCreate--");
        Timber.i("%s == %s", "Git-SHA", BuildConfig.GIT_SHA);
        Timber.i("%s == %s", "Build-time", BuildConfig.BUILD_TIME);
        Timber.i("%s == %s", "Config", config);
    }

    public static TwitterChampionsApplication get(Activity activity) {
        final Application app = activity.getApplication();
        if (app instanceof TwitterChampionsApplication) {
            return ((TwitterChampionsApplication)app);
        } else {
            throw new IllegalArgumentException("activity should belong to TwitterChampionsApplication");
        }
    }

    public static TwitterChampionsApplication get(Fragment fragment) {
        return TwitterChampionsApplication.get(fragment.getActivity());
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public void resetConfig(Config config) {
        new AppConfigurator().saveConfig(this, config);
        final Intent intent = new Intent(this, ChampionsActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        final AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        System.exit(0);
    }

    private void prepareObjectGraph() {
        final Object serviceModule = config.isTwitterUseMock() ? new MockServiceModule() : new RealServiceModule(config);
        objectGraph = ObjectGraph.create(new AppModule(this, config), serviceModule);
    }

    private void prepareLogs() {
        if (config.isWriteLogs()) {
            Timber.plant(new Timber.DebugTree());
            Picasso.with(this).setDebugging(true);
        }
        if (config.isEnableCrashlytics()) {
            Timber.plant(new CrashlyticsTree());
        }
    }
}
