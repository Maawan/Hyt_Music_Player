package com.hayatsoftwares.hyt.music.player.Notifications;

import static com.hayatsoftwares.hyt.music.player.Notifications.ApplicationClass.CHANNEL_ID_1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hayatsoftwares.hyt.music.player.MusicService.MyService;

public class NotificationReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PREV = "PREVIOUS";
    public static final String ACTION_PLAY = "PLAY";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context , MyService.class);
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case ACTION_PLAY:
                    Toast.makeText(context, "PLay", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("actionName" , intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_NEXT:
                    Toast.makeText(context, "NEXT", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("actionName" , intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PREV:
                    Toast.makeText(context, "PREV", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("actionName" , intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }
    }
}
