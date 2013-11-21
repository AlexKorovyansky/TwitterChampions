package com.alexkorovyansky.twitterchampions.app.config;

import com.alexkorovyansky.twitterchampions.BuildConfig;
import com.google.gson.Gson;

import javax.inject.Singleton;

/**
 * Config
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
@Singleton
public class Config {
    private final boolean twitterUseMock;
    private final boolean writeLogs;
    private final boolean enableCrashlytics;
    private final String[] hashTags;

    public static class Builder{

        private boolean twitterUseMock;
        private boolean writeLogs;
        private boolean enableCrashlytics;
        private String[] hashTags;

        public Builder() {
            this.twitterUseMock = false;
            this.writeLogs = false;
            this.enableCrashlytics = false;
            this.hashTags = BuildConfig.HASH_TAGS;
        }

        public Builder twitterUseMock(boolean serviceUseMock) {
            this.twitterUseMock = serviceUseMock;
            return this;
        }

        public Builder writeLogs(boolean writeLogs) {
            this.writeLogs = writeLogs;
            return this;
        }

        public Builder enableCrashlytics(boolean enableCrashlytics) {
            this.enableCrashlytics = enableCrashlytics;
            return this;
        }

        public Builder hashTags(String[] hashTags) {
            this.hashTags = new String[hashTags.length];
            for (int i = 0; i < hashTags.length; ++i) {
                if (hashTags[i].startsWith("#")) {
                    this.hashTags[i] = hashTags[i].substring(1);
                } else {
                    this.hashTags[i] = hashTags[i];
                }
            }
            return this;
        }

        public Config build() {
            return new Config(twitterUseMock, writeLogs, enableCrashlytics, hashTags);
        }
    }

    private Config(boolean twitterUseMock, boolean writeLogs, boolean enableCrashlytics, String[] hashTags) {
        this.twitterUseMock = twitterUseMock;
        this.writeLogs = writeLogs;
        this.enableCrashlytics = enableCrashlytics;
        this.hashTags = hashTags;
    }

    public boolean isTwitterUseMock() {
        return twitterUseMock;
    }

    public boolean isWriteLogs() {
        return writeLogs;
    }

    public boolean isEnableCrashlytics() {
        return enableCrashlytics;
    }

    public String[] getHashTags() {
        return hashTags;
    }

    public String getHashTagsAsString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (String hashTag: hashTags) {
            stringBuilder.append("#" + hashTag + " ");
        }
        return stringBuilder.toString().trim();
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public static Config fromJSON(String json) {
        return new Gson().fromJson(json, Config.class);
    }
}
