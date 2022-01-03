package com.hayatsoftwares.hyt.music.player.Interfaces;

import com.hayatsoftwares.hyt.music.player.Models.Song;

import java.util.ArrayList;

public interface Interactor {
    void itemCliked(ArrayList<Song> list , int pos);
}
