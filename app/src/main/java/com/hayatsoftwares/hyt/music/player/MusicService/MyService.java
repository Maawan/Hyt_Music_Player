package com.hayatsoftwares.hyt.music.player.MusicService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hayatsoftwares.hyt.music.player.Interfaces.MusicPlay;

public class MyService extends Service {
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PREV = "PREVIOUS";
    public static final String ACTION_PLAY = "PLAY";
    MusicPlay listener;
    @Nullable
    private IBinder mBinder = new MyBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName = intent.getStringExtra("actionName");
        if(actionName != null){
            switch (actionName){
                case ACTION_PLAY:
                   if(listener != null){
                       listener.playPauseClicked();
                   }
                    break;
                case ACTION_NEXT:
                   if(listener != null){
                       listener.nextClicked();
                   }
                    break;
                case ACTION_PREV:
                    if(listener != null){
                        listener.prevClicked();
                    }
                    break;
            }
        }
        return START_STICKY;
    }
    public void setCallBack(MusicPlay listener){
        this.listener = listener;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class MyBinder extends Binder{
        public MyService getServices(){
            return MyService.this;
        }
    }
}
