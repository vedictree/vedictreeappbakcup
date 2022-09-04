package com.vedictree.preschool;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager implements MediaPlayer.OnPreparedListener {
    static MediaPlayer mPlayer;
    Context context;
    private int mySoundId;

    public MusicManager(Context ctx, int musicID) {
        context = ctx;
        mySoundId = musicID;
        mPlayer = MediaPlayer.create(context, mySoundId);
        mPlayer.setOnPreparedListener(this);
    }

    public void play() {
        mPlayer = MediaPlayer.create(context, mySoundId);

    }

    public void stop() {
        mPlayer.stop();
//        mPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPlayer.start();
        mPlayer.setLooping(true);
//        mPlayer.setVolume(25, 25);
    }
}
