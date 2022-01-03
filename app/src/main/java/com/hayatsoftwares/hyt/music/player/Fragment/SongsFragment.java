package com.hayatsoftwares.hyt.music.player.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.hayatsoftwares.hyt.music.player.MainActivity.*;

import com.hayatsoftwares.hyt.music.player.Adapter.SongsAdapter;
import com.hayatsoftwares.hyt.music.player.Interfaces.ClickEvent;
import com.hayatsoftwares.hyt.music.player.Interfaces.Interactor;
import com.hayatsoftwares.hyt.music.player.MainActivity;
import com.hayatsoftwares.hyt.music.player.Models.Song;
import com.hayatsoftwares.hyt.music.player.R;

import java.util.ArrayList;

public class SongsFragment extends Fragment implements ClickEvent {
    private View view;
    private RecyclerView recyclerView;
    SongsAdapter songsAdapter;
    private Interactor listener;

    public SongsFragment(Interactor list){
        this.listener = list;
    }
    @Override
    public void onResume() {
        super.onResume();
        recyclerView = view.findViewById(R.id.recycler);
        songsAdapter = new SongsAdapter(getContext() , currentSongs , this);
        recyclerView.setAdapter(songsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_songs , container , false);
        return view;
    }

    @Override
    public void itemClickedOnMainList(ArrayList<Song> list, int position) {
        listener.itemCliked(list , position);
    }


}
