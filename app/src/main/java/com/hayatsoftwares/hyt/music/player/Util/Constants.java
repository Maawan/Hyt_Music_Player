package com.hayatsoftwares.hyt.music.player.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.hayatsoftwares.hyt.music.player.Models.Song;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class Constants {
    public static final int PERMISSION_CODE = 2120;
    public static Bitmap getSongBitmap(Song song){
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
        retriever.setDataSource(song.getPath());
        byte[] data = retriever.getEmbeddedPicture();
        if(data != null){
            return BitmapFactory.decodeByteArray(data , 0 , data.length);
        }
        return null;
    }
}
