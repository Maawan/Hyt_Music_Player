package com.hayatsoftwares.hyt.music.player.Interfaces;

import com.hayatsoftwares.hyt.music.player.Models.Song;

import java.io.IOException;
import java.util.ArrayList;

public interface ClickEvent {
    void itemClickedOnMainList(ArrayList<Song> list , int position);
}
