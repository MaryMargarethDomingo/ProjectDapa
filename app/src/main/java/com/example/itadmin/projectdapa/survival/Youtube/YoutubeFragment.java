package com.example.itadmin.projectdapa.survival.Youtube;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.MainActivity;
import com.example.itadmin.projectdapa.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class YoutubeFragment extends Fragment {

    private YouTubePlayer YPlayer;

    private String apiKey = "AIzaSyBvSLp9j1nAV4CPRprUZkWLO-1_Gt_45Xk";

    private String linkEarthquake = "BLEPakj1YTY";
    private String linkTyphoon = "xHRbnuB9F1I";
    private String linkFlood = "43M5mZuzHF8";
    private String linkTsunami = "m7EDddq9ftQ";
    private String linkVolcano = "Z-w_z9yobpE";
    private String chosen = "";

    public YoutubeFragment(){

    }

    public YoutubeFragment(int index){
        switch (index){
            case 0:
                chosen = linkEarthquake;
                break;
            case 1:
                chosen = linkTyphoon;
                break;
            case 2:
                chosen = linkFlood;
                break;
            case 3:
                chosen = linkTsunami;
                break;
            case 4:
                chosen = linkVolcano;
                break;
            default:
                chosen = linkEarthquake;
        }
    }

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            MainActivity myContext = (MainActivity) activity;
        }

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_youtube_layout, container, false);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtubeplayerfragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize("DEVELOPER_KEY", new OnInitializedListener() {
            @Override
            public void onInitializationSuccess(Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    YPlayer.loadVideo(chosen);
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });
        return rootView;
    }
}
