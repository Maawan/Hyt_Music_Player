package com.hayatsoftwares.hyt.music.player.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hayatsoftwares.hyt.music.player.Interfaces.ClickEvent;
import com.hayatsoftwares.hyt.music.player.Models.Song;
import com.hayatsoftwares.hyt.music.player.R;
import com.hayatsoftwares.hyt.music.player.Util.Constants;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {
    private ArrayList<Song> songs;
    private Context ctx;
    private ClickEvent listener;

    public SongsAdapter(Context ctx,ArrayList<Song> song , ClickEvent event){
        this.ctx = ctx;
        this.listener = event;
        this.songs = song;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context thisContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(thisContext);
        View songView = inflater.inflate(R.layout.list_layout , parent , false);
        return new MyViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bitmap bitMap = Constants.getSongBitmap(songs.get(position));
        if(bitMap != null){
            holder.image.setImageBitmap(bitMap);
        }else{
            holder.image.setImageResource(R.drawable.ic_baseline_music_note_24);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClickedOnMainList(songs , holder.getAdapterPosition());
            }
        });

        int seconds = (int) (Integer.valueOf(songs.get(position).getDuration()) / 1000) % 60 ;
        String sec="";
        if(seconds < 10){
            sec = "0"+seconds;
        }else{
            sec = String.valueOf(seconds);
        }
        int minutes = (int) ((Integer.valueOf(songs.get(position).getDuration()) / (1000*60)) % 60);
        holder.songName.setText(songs.get(position).getDisplayName());
        holder.songDesc.setText(songs.get(position).getAlbum() + " | " + songs.get(position).getArtist() + " | " + minutes+":"+sec);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image ;
        TextView songName;
        TextView songDesc;
        RelativeLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            image = itemView.findViewById(R.id.photo);
            songName = itemView.findViewById(R.id.songName);
            songDesc = itemView.findViewById(R.id.songDesc);
        }
    }
}
