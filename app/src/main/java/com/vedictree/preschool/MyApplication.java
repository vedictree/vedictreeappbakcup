package com.vedictree.preschool;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class MyApplication extends Application {
    MyAppsNotificationManager  myAppsNotificationManager;

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        stopService(new Intent(this,MusicBackground.class));
//    }


    @Override
        public void onCreate() {
            super.onCreate();

//            startService(new Intent(this,MusicBackground.class));

            registerActivityLifecycleCallbacks(new MyLifecycleHandler());
            myAppsNotificationManager = MyAppsNotificationManager.getInstance(this);
            myAppsNotificationManager.registerNotificationChannelChannel(
                    getString(R.string.NEWS_CHANNEL_ID),
                    getString(R.string.CHANNEL_NEWS),
                    getString(R.string.CHANNEL_DESCRIPTION));

        myAppsNotificationManager.registerNotificationChannelChannel(
                getString(R.string.NOTI_CHANNEL_ID),
                getString(R.string.CHANNEL_NOTI),
                getString(R.string.CHANNEL_DESCRIPTION_NOTI));

        myAppsNotificationManager.registerNotificationChannelChannel(
                getString(R.string.HOME_CHANNEL_ID),
                getString(R.string.CHANNEL_HOME),
                getString(R.string.CHANNEL_DESCRIPTION_HOME));


    }



    public void triggerNotification(Class targetNotificationActivity, String channelId, String title, String text, String bigText, int priority, boolean autoCancel, int notificationId, int pendingIntentFlag){
        myAppsNotificationManager.triggerNotification(targetNotificationActivity,channelId,title,text, bigText, priority, autoCancel,notificationId, pendingIntentFlag);
    }

    public void triggerNotification(Class targetNotificationActivity, String channelId, String title, String text, String bigText, int priority, boolean autoCancel, int notificationId){
        myAppsNotificationManager.triggerNotification(targetNotificationActivity,channelId,title,text, bigText, priority, autoCancel,notificationId);
    }

    public void updateNotification(Class targetNotificationActivity,String title,String text, String channelId, int notificationId, String bigpictureString, int pendingIntentflag){
        myAppsNotificationManager.updateWithPicture(targetNotificationActivity, title, text, channelId, notificationId, bigpictureString, pendingIntentflag);
    }

    public void cancelNotification(int notificaitonId){
        myAppsNotificationManager.cancelNotification(notificaitonId);
    }
}
