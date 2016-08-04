/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.techmighty.baseplayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.techmighty.baseplayer.R;
import com.techmighty.baseplayer.adapters.ArtistSongAdapter;
import com.techmighty.baseplayer.dataloaders.ArtistSongLoader;
import com.techmighty.baseplayer.models.Song;
import com.techmighty.baseplayer.utils.Constants;
import com.techmighty.baseplayer.widgets.DividerItemDecoration;

import java.util.ArrayList;

public class ArtistMusicFragment extends Fragment {

    public static RecyclerView songsRecyclerview;
    long artistID = -1;
    ArtistSongAdapter mSongAdapter;

    public static ArtistMusicFragment newInstance(long id) {
        ArtistMusicFragment fragment = new ArtistMusicFragment();
        Bundle args = new Bundle();
        args.putLong(Constants.ARTIST_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artistID = getArguments().getLong(Constants.ARTIST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_artist_music, container, false);

        songsRecyclerview = (RecyclerView) rootView.findViewById(R.id.recycler_view_songs);

        setUpSongs();


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MobileAds.initialize(getActivity(), getActivity().getString(R.string.banner_2));
        AdView mAdView = (AdView) view.findViewById(R.id.adViewDetail);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("7A96583646EE2BA8273666EA4084F51A").build();

        mAdView.loadAd(adRequest);
    }

    private void setUpSongs() {
        songsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Song> songList;
        songList = ArtistSongLoader.getSongsForArtist(getActivity(), artistID);

        // adding one dummy song to top of arraylist
        //there will be albums header at this position in recyclerview
        songList.add(0, new Song(-1, -1, -1, "dummy", "dummy", "dummy", -1, -1));

        mSongAdapter = new ArtistSongAdapter(getActivity(), songList, artistID);
        songsRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        songsRecyclerview.setAdapter(mSongAdapter);
    }


}
