package com.hayatsoftwares.hyt.music.player.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hayatsoftwares.hyt.music.player.Fragment.PlayListFragment;
import com.hayatsoftwares.hyt.music.player.Fragment.SongsFragment;
import com.hayatsoftwares.hyt.music.player.Interfaces.Interactor;
import com.hayatsoftwares.hyt.music.player.Interfaces.Interactor2;
import com.hayatsoftwares.hyt.music.player.Models.Song;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter implements Interactor {
    private Interactor2 listener2;


    public FragmentAdapter(Interactor2 list , FragmentManager fragmentManager){
        super(fragmentManager);
        this.listener2 = list;

    }
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new SongsFragment(this);
        }else {
            return new PlayListFragment(this);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public void itemCliked(ArrayList<Song> list, int pos) {
        listener2.itemClikedinFrag(list , pos);
    }
}
