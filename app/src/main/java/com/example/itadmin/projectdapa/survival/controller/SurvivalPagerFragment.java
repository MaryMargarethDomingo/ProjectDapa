package com.example.itadmin.projectdapa.survival.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.itadmin.projectdapa.R;

public class SurvivalPagerFragment extends Fragment {

    private String fileName = "";

    private Button btnBefore;
    private Button btnDuring;
    private Button btnAfter;

    private WebViewContentFragment webViewContentFragment = new WebViewContentFragment();
    Bundle args = new Bundle();

    public SurvivalPagerFragment() {    }

    @SuppressLint("ValidFragment")
    public SurvivalPagerFragment(int index){

        switch (index){
            case 1:
                fileName = "earthquake_";
                break;

            case 2:
                fileName = "typhoon_";
                break;

            case 3:
                fileName = "flood_";
                break;

            case 4:
                fileName = "tsunami_";
                break;

            case 5:
                fileName = "volcano_";
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.survival_pager, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        btnBefore = getView().findViewById(R.id.btnBefore);
        btnDuring = getView().findViewById(R.id.btnDuring);
        btnAfter = getView().findViewById(R.id.btnAfter);

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                args.putString("filename", fileName + "before.html");
                webViewContentFragment.setArguments(args);

                getFragmentManager().beginTransaction().add(R.id.pagerID, webViewContentFragment).addToBackStack(null).commit();
            }
        });

        btnDuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                args.putString("filename", fileName + "during.html");
                webViewContentFragment.setArguments(args);

                getFragmentManager().beginTransaction().add(R.id.pagerID, webViewContentFragment).addToBackStack(null).commit();
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                args.putString("filename", fileName + "after.html");
                webViewContentFragment.setArguments(args);

                getFragmentManager().beginTransaction().add(R.id.pagerID, webViewContentFragment).addToBackStack(null).commit();
            }
        });

    }



}
