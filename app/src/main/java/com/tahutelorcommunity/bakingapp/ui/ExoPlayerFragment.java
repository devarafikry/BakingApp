package com.tahutelorcommunity.bakingapp.ui;


import android.graphics.BitmapFactory;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tahutelorcommunity.bakingapp.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExoPlayerFragment extends Fragment{
    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView exo_player_view;

    SimpleExoPlayer mExoplayer;
    public static final String MEDIA_URI_KEY = "mediaUri";
    public static final String POSITION_BUNDLE_KEY = "position";

    private long position ;
    private Uri uri;
    private String s_thumbnailUri;
    public ExoPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(POSITION_BUNDLE_KEY, position);
    }
//
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exo_player, container, false);
        ButterKnife.bind(this, view);
        if(savedInstanceState != null){
            position = savedInstanceState.getLong(POSITION_BUNDLE_KEY);
        }
        uri = null;
        if(getArguments() != null){
            if(getArguments().getString(MEDIA_URI_KEY) != null){
                uri = Uri.parse(getArguments().getString(MEDIA_URI_KEY));
            }

        }

        if(uri != null){
            initializePlayer(uri, position);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mExoplayer != null){
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mExoplayer == null){
            initializePlayer(uri,position);
        }
    }

    private void initializePlayer(Uri mediaUri, long position_seek){
        mExoplayer = ExoPlayerFactory.newSimpleInstance(
                getContext(),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );
        String userAgent = Util.getUserAgent(getContext(),
                "BakingApp");

        MediaSource mediaSource = new ExtractorMediaSource(
                mediaUri,
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(),
                null,
                null
        );
        mExoplayer.prepare(mediaSource);
        mExoplayer.setPlayWhenReady(true);
        if(TextUtils.isEmpty(mediaUri.toString())){
            exo_player_view.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.no_video_available));
            exo_player_view.setUseController(false);
        }
        exo_player_view.setPlayer(mExoplayer);
        exo_player_view.setBackgroundColor(getResources().getColor(android.R.color.black));
        mExoplayer.seekTo(position_seek);
    }

    private void releasePlayer(){
        if(mExoplayer != null){
            position = mExoplayer.getCurrentPosition();
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
