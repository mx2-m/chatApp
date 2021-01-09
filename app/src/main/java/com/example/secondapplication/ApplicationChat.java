package com.example.secondapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ApplicationChat extends Application {

    SharedPreferences sharedPreferences;
    boolean music;
    boolean sound;
    boolean vibration;
    public static final String CHANNEL_1_ID = "friendsRequest";
    public static final String CHANNEL_2_ID = "newMessage";

    @Override
    public void onCreate() {
        createNotificationChannels();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        music = sharedPreferences.getBoolean("music", true);
        sound = sharedPreferences.getBoolean("sound", true);
        vibration = sharedPreferences.getBoolean("vibration", true);
        super.onCreate();
    }

    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "friendsRequest",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Friends request");
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "newMessage",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("New messsage");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }

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
