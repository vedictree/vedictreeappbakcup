package com.vedictree.preschool;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;
    @Override
    public IBinder onBind (Intent arg0) {
        Log.i("NotificationService","NotificationService");
        return null;

    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        Log.i ( TAG , "onStartCommand" ) ;
        super .onStartCommand(intent , flags , startId) ;
        startTimer() ;
        return START_STICKY ;
    }
    @Override
    public void onCreate () {
        Log.i ( TAG , "onCreate" ) ;
    }
    @Override
    public void onDestroy () {
        Log.i( TAG , "onDestroy" ) ;
        stopTimerTask() ;
        super .onDestroy() ;
    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        timer .schedule( timerTask , 5000 , Your_X_SECS * 1000 ) ; //
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {
                        createNotification() ;
                    }
                }) ;
            }
        } ;
    }
    private void createNotification () {
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
//        mBuilder.setContentTitle( "My Notification" ) ;
//        mBuilder.setContentText( "Notification Listener Service Example" ) ;
//        mBuilder.setTicker( "Notification Listener Service Example" ) ;
//        mBuilder.setSmallIcon(R.drawable.ic_logo_tree ) ;
//        mBuilder.setAutoCancel( true ) ;
//        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//            int importance = NotificationManager. IMPORTANCE_HIGH ;
//            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//            assert mNotificationManager != null;
//            mNotificationManager.createNotificationChannel(notificationChannel) ;
//        }
//        assert mNotificationManager != null;
//        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
//    }

        int NOTIFICATION_ID = 1;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "my_channel_01";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Vedic Tree";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_logo_tree)
                    .setContentTitle("Vedic Tree Kids Learning App")
                    .setContentText("Test")
                    .setAutoCancel(true);

            Intent resultIntent = new Intent(getApplicationContext(), NotificationActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addParentStack(NotificationActivity.class);
            stackBuilder.addNextIntent(resultIntent);
//                            editor.putString("NOTIFICATION_FROM", "STATUS_BAR");
//                            editor.commit();
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}
