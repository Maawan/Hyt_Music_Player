package com.hayatsoftwares.hyt.music.player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.hayatsoftwares.hyt.music.player.Adapter.SongsAdapter;
import com.hayatsoftwares.hyt.music.player.Interfaces.ClickEvent;
import com.hayatsoftwares.hyt.music.player.Models.Song;
import com.hayatsoftwares.hyt.music.player.Util.Constants;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickEvent {
    ArrayList<Song> currentSongs;
    private SeekBar seekBar;
    //private
    private Handler handler,mHandler;
    int seeked_progess = 0;
    private Runnable runnable;
    int pos = -1;
    private LinearLayout swipeSongPanel;
    TextView title;
    TextView artistName;
    CoordinatorLayout layout;
    private ImageView imageSwipe;
    private TextView songTitleSwipe;
    private TextView artistNameSwipe;
    ImageView playPauseSwipe;
    private ImageView nextButton , prevButton;
    private ImageView image;
    MediaPlayer mediaPlayer = null;
    private LinearLayout mBottomSheetLayout;
    private BottomSheetBehavior sheetBehavior;
    private ImageView header_Arrow_Image;
    private ImageView playPauseButton;
    private TextView startTime , endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini();
        currentSongs = new ArrayList<>();
        handler = new Handler();
        mHandler = new Handler();
        if (!checkPermissions()) {
            Toast.makeText(this, "Storage Permissions are not Available", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
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
        Cursor cursor = getContentResolver().query(uri, projection, selection, null, null);
        currentSongs.clear();
        while (cursor.moveToNext()) {
            //Toast.makeText(this, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)) + " ", Toast.LENGTH_SHORT).show();
            Log.e("Song -> ", cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
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
        Toast.makeText(this, "Found " + currentSongs.size() + " songs in your device !", Toast.LENGTH_LONG).show();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        SongsAdapter adapter = new SongsAdapter(this, currentSongs, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    swipeSongPanel.setVisibility(View.VISIBLE);
                }else{
                    swipeSongPanel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //header_Arrow_Image = findViewById(R.id.bottom_sheet_arrow);
        image = findViewById(R.id.image);
        title = findViewById(R.id.title);
        artistName = findViewById(R.id.artirstNme);
        seekBar = findViewById(R.id.seekBar);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        playPauseSwipe.setImageResource(R.drawable.ic_baseline_play_arrow_24_swipe);
                    }else{
                        playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
                        playPauseSwipe.setImageResource(R.drawable.ic_baseline_pause_24_swipe);
                        mediaPlayer.start();
                    }
                }

            }
        });
        playPauseSwipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        playPauseSwipe.setImageResource(R.drawable.ic_baseline_play_arrow_24_swipe);
                    }else{
                        mediaPlayer.start();
                        playPauseSwipe.setImageResource(R.drawable.ic_baseline_pause_24_swipe);
                    }
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seeked_progess = progress;
                seeked_progess = seeked_progess ;

                if (fromUser) {
                    Runnable mRunnable = new Runnable() {

                        @Override
                        public void run() {
                            int min, sec;

                            if (mediaPlayer != null /*Checking if the
                       music player is null or not otherwise it
                       may throw an exception*/) {
                                int mCurrentPosition = seekBar.getProgress();

                                min = mCurrentPosition / 60;
                                sec = mCurrentPosition % 60;

                                Log.e("Music Player Activity", "Minutes : "+min +" Seconds : " + sec);

                        /*currentime_mm.setText("" + min);
                        currentime_ss.setText("" + sec);*/
                            }
                            mHandler.postDelayed(this, 1000);
                        }
                    };
                    mRunnable.run();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(MainActivity.this, "Progress chnaged" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                mediaPlayer.seekTo(seeked_progess);
                mediaPlayer.start();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = pos + 1;
                if(pos >= currentSongs.size()){
                    pos = 0;
                }
                try {
                    playMusic(currentSongs.get(pos));
                }catch (Exception e){

                }

            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = pos - 1;
                if(pos < 0){
                   pos = currentSongs.size() - 1;
                }
                try{
                    playMusic(currentSongs.get(pos));
                }catch (Exception e){

                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();

    }

    private void ini(){
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        layout = findViewById(R.id.sheetLayout);
        if(mediaPlayer == null){
            layout.setVisibility(View.GONE);
        }
        playPauseButton = findViewById(R.id.playPauseButton);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        imageSwipe = findViewById(R.id.imageSwipe);
        songTitleSwipe = findViewById(R.id.songTile);
        artistNameSwipe = findViewById(R.id.artistNameSwipe);
        playPauseSwipe = findViewById(R.id.playPauseSwipeButton);
        swipeSongPanel = findViewById(R.id.bottom_sheet_header);

    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void itemClickedOnMainList(ArrayList<Song> list, int position)  {
        //Toast.makeText(this, "Item CLiked " + list.get(position).getPath(), Toast.LENGTH_SHORT).show();
        try{
            pos = position;
            playMusic(list.get(position));
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Exception has been Occured"+e.getLocalizedMessage() + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void playMusic(Song song) throws IOException {
        if (mediaPlayer == null) {
            try {
                mediaPlayer = new MediaPlayer();
                startSong(song);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer.pause();
            mediaPlayer.stop();
            startSong(song);

        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(++pos < currentSongs.size()){
                    try {
                        playMusic(currentSongs.get(pos));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });




    }
    public void initialiseSeekbar(MediaPlayer mediaPlayer){
        seekBar.setProgress(mediaPlayer.getDuration() / 1000);
        int seconds = (int) (Integer.valueOf(mediaPlayer.getDuration()) / 1000) % 60 ;
        String sec="";
        if(seconds < 10){
            sec = "0"+seconds;
        }else{
            sec = String.valueOf(seconds);
        }
        int minutes = (int) ((Integer.valueOf(mediaPlayer.getDuration()) / (1000*60)) % 60);
        endTime.setText(minutes+":"+sec);
        runnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000; // In milliseconds
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    seekBar.setMax(mediaPlayer.getDuration());
                    int seconds = (int) (Integer.valueOf(mediaPlayer.getCurrentPosition()) / 1000) % 60 ;
                    String sec="";
                    if(seconds < 10){
                        sec = "0"+seconds;
                    }else{
                        sec = String.valueOf(seconds);
                    }
                    int minutes = (int) ((Integer.valueOf(mediaPlayer.getCurrentPosition()) / (1000*60)) % 60);
                    startTime.setText(minutes+":"+sec);

                }
                handler.postDelayed(runnable , 1000);
            }
        };
        handler.postDelayed(runnable , 1000);
    }
    public void startSong(Song song) throws IOException{
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }else{
            mediaPlayer.stop();
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
        }


        layout.setVisibility(View.VISIBLE);
        mediaPlayer.setDataSource(song.getPath());
        mediaPlayer.prepare();
        mediaPlayer.start();
        playPauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
        playPauseSwipe.setImageResource(R.drawable.ic_baseline_pause_24_swipe);
        songTitleSwipe.setText(song.getDisplayName());
        artistNameSwipe.setText(song.getArtist());
        title.setText(song.getDisplayName());
        artistName.setText(song.getAlbum());
        title.setSelected(true);
        songTitleSwipe.setSelected(true);

        Bitmap bitmap = Constants.getSongBitmap(song);
        initialiseSeekbar(mediaPlayer);
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
            imageSwipe.setImageBitmap(bitmap);
        }

    }


}