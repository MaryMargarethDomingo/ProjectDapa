package com.example.itadmin.projectdapa.survival.Youtube;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.itadmin.projectdapa.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeFragment extends Fragment {

    private YouTubePlayerView youtubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_youtube_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        youtubePlayerView = getView().findViewById(R.id.youtube_view);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("Iuea4NzqMfI");
                youtubePlayerView.initialize("AIzaSyBvSLp9j1nAV4CPRprUZkWLO-1_Gt_45Xk", onInitializedListener);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        button = getView().findViewById(R.id.btnPlay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubePlayerView.initialize("AIzaSyBvSLp9j1nAV4CPRprUZkWLO-1_Gt_45Xk", onInitializedListener);
            }
        });
    }

}
