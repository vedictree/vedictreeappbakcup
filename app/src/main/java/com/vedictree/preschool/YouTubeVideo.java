package com.vedictree.preschool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class YouTubeVideo extends YouTubeBaseActivity {
    String api_key = "AIzaSyDWBSre_GD1UtuFfxSx4GmDmGcKQOCcY6o";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String video_id=preferences.getString("YOU_TUBE_ID","");
        Log.i("video_id",video_id);
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.you_tube_player_view);

        youTubePlayerView.initialize(api_key,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(video_id);
                        Log.i("Provider", String.valueOf(video_id));
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.i("Error", String.valueOf(youTubeInitializationResult));
                    }
                });




    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isMyServiceRunning(MusicBackground.class)){
        }else {
            startService(new Intent(this,MusicBackground.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isApplicationGoingToBackground(this))
        {
            stopService(new Intent(this,MusicBackground.class));
        }else {

        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isApplicationGoingToBackground(final Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                Log.i("App b","a3");
                return true;
            }

        }
        Log.i("App b","b3");
        return false;
    }

}