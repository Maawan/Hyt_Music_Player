package com.hayatsoftwares.hyt.music.player.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
        holder.menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ctx , holder.menu_button);
                popupMenu.inflate(R.menu.songs_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.playNext:
                                Toast.makeText(ctx, "Play Next Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.playSongFromMenu:
                                Toast.makeText(ctx, "Play Song from Menu", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.addToFav:
                                Toast.makeText(ctx, "Add to Fav", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.songInfo:
                                Toast.makeText(ctx, "Songs Info Clicked", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
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
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(ctx, "Long Pressed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                holder.heart.setImageResource(R.drawable.heart_filled);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image ;
        TextView songName;
        ImageView menu_button;
        TextView songDesc;
        RelativeLayout layout;
        ImageView heart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            heart = itemView.findViewById(R.id.heart_btn);
            menu_button = itemView.findViewById(R.id.menu_btn);
            image = itemView.findViewById(R.id.photo);
            songName = itemView.findViewById(R.id.songName);
            songDesc = itemView.findViewById(R.id.songDesc);
        }
    }
}
