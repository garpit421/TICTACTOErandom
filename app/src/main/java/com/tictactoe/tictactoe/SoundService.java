package com.tictactoe.tictactoe;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import org.jetbrains.annotations.Nullable;

public class SoundService extends Service {
    private MediaPlayer player;
    @Nullable
  //  @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {              // its bind the server with activity
        return null;
    }
    @Override
    public  int onStartCommand(Intent intent,int flags,int startId){
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}
