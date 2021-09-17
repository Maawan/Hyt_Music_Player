package com.hayatsoftwares.mybee.music.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.hayatsoftwares.mybee.music.player.Models.Song;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Song> currentSongs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentSongs = new ArrayList<>();
        if(!checkPermissions()){
            Toast.makeText(this, "Storage Permissions are not Available", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this , SplashActivity.class));
        }
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver().query(uri , projection , selection , null , null );
        currentSongs.clear();
        while(cursor.moveToNext()){
            Toast.makeText(this, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)) + " ", Toast.LENGTH_SHORT).show();
            Log.e("Song -> " , cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            currentSongs.add(new Song(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));
        }
        cursor.close();


    }
    private boolean checkPermissions(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

}