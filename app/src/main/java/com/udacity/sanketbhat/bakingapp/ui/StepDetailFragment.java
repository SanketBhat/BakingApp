package com.udacity.sanketbhat.bakingapp.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Step;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {
    public static final String STEP_ITEM = "StepItem";
    private static final String EXTRA_PLAY_WHEN_READY = "playWhenReady";
    private static final String EXTRA_WINDOW_INDEX = "currentWindowIndex";
    private static final String EXTRA_CURRENT_POSITION = "currentSeekPosition";
    Uri videoUri;
    private Step stepItem;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private PlayerEventListener playerEventListener;
    private ProgressBar bufferingProgressBar;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_ITEM)) {
            stepItem = getArguments().getParcelable(STEP_ITEM);
            playerEventListener = new PlayerEventListener();
        } else {
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(EXTRA_PLAY_WHEN_READY, true);
            currentWindow = savedInstanceState.getInt(EXTRA_WINDOW_INDEX, 0);
            playbackPosition = savedInstanceState.getLong(EXTRA_CURRENT_POSITION, 0);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        playerView = rootView.findViewById(R.id.exoPlayerView);
        bufferingProgressBar = rootView.findViewById(R.id.bufferProgressBar);


        if (stepItem != null) {
            ((TextView) rootView.findViewById(R.id.stepShortDescription)).setText(stepItem.getShortDescription());
            ((TextView) rootView.findViewById(R.id.stepFullDescription)).setText(stepItem.getDescription());
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_PLAY_WHEN_READY, playWhenReady);
        outState.putLong(EXTRA_CURRENT_POSITION, playbackPosition);
        outState.putInt(EXTRA_WINDOW_INDEX, currentWindow);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= 23) {
            initializePlayer();
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());

        player.addListener(playerEventListener);
        playerView.setPlayer(player);


        videoUri = Uri.parse(stepItem.getVideoURL());
        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name))).createMediaSource(videoUri);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
        player.setPlayWhenReady(playWhenReady);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        playWhenReady = player.getPlayWhenReady();
        currentWindow = player.getCurrentWindowIndex();
        playbackPosition = player.getCurrentPosition();
        player.stop();
        player.release();
    }

    private class PlayerEventListener extends Player.DefaultEventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    bufferingProgressBar.setVisibility(View.VISIBLE);
                    break;

                default:
                    //We don't need progressbar for all other states
                    bufferingProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
