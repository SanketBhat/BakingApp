package com.udacity.sanketbhat.bakingapp.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
    private static final String TAG = "StepDetailFragment";
    private Step stepItem;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private PlayerEventListener playerEventListener;
    private ProgressBar bufferingProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(STEP_ITEM)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            stepItem = getArguments().getParcelable(STEP_ITEM);
            playerEventListener = new PlayerEventListener();

//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                // TODO: 31-08-2018 Change with appropriate one
//                appBarLayout.setTitle(stepItem.getShortDescription());
//            }
        } else {
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        playerView = rootView.findViewById(R.id.exoPlayerView);
        bufferingProgressBar = rootView.findViewById(R.id.bufferProgressBar);
        // Show the dummy content as text in a TextView.
        if (stepItem != null) {
            ((TextView) rootView.findViewById(R.id.stepShortDescription)).setText(stepItem.getShortDescription());
            ((TextView) rootView.findViewById(R.id.stepFullDescription)).setText(stepItem.getDescription());
        }

        return rootView;
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        player.setPlayWhenReady(true);
        player.addListener(playerEventListener);
        playerView.setPlayer(player);

        Uri uri = Uri.parse(stepItem.getVideoURL());
        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name))).createMediaSource(uri);

        player.prepare(mediaSource, true, false);
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

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= 24) {
            releasePlayer();
        }
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
