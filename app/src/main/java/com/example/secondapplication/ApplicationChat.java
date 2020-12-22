package com.example.secondapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApplicationChat extends Application {

    SharedPreferences sharedPreferences;
    boolean music;
    boolean sound;
    boolean vibration;

    @Override
    public void onCreate() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        music = sharedPreferences.getBoolean("music", true);
        sound = sharedPreferences.getBoolean("sound", true);
        vibration = sharedPreferences.getBoolean("vibration", true);
        super.onCreate();
    }


    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }
}
