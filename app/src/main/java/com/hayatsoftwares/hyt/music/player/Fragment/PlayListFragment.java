package com.hayatsoftwares.hyt.music.player.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hayatsoftwares.hyt.music.player.Interfaces.Interactor;
import com.hayatsoftwares.hyt.music.player.R;

import java.util.zip.Inflater;

public class PlayListFragment extends Fragment {
    private View view;
    private Interactor interactor;
    public PlayListFragment(Interactor interactor){
        this.interactor = interactor;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist , container , false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
