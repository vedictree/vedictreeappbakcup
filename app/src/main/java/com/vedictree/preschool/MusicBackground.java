package com.vedictree.preschool;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicBackground extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final String LOGCAT = null;
    MediaPlayer objPlayer;


//    public void onCreate(){
//        objPlayer.start();
//    }
//
    public int onStartCommand(Intent intent, int flags, int startId){
        objPlayer = MediaPlayer.create(this, R.raw.background_music);
        objPlayer.setLooping(true);
        objPlayer.start();
        return START_STICKY;
    }

    public void onStop(){
        objPlayer.stop();
        objPlayer.release();
    }

    public void onResume(){
        objPlayer.start();
    }

    public void onPause(){
        objPlayer.stop();
        objPlayer.release();
    }
    public void onDestroy(){
        objPlayer.stop();
        objPlayer.release();
    }
}
