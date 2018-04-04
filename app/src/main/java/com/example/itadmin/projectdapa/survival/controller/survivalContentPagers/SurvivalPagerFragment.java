package com.example.itadmin.projectdapa.survival.controller.survivalContentPagers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.itadmin.projectdapa.R;

public class SurvivalPagerFragment extends Fragment {

    private String fileName = "";

    private ToggleButton togBefore;
    private ToggleButton togDuring;
    private ToggleButton togAfter;

    private WebView webView;

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
        return inflater.inflate(R.layout.survival_webview_content, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        webView = getView().findViewById(R.id.webview1);

        togBefore = getView().findViewById(R.id.togBefore);
        togDuring = getView().findViewById(R.id.togDuring);
        togAfter = getView().findViewById(R.id.togAfter);

        togBefore.setOnCheckedChangeListener(changeChecker);
        togDuring.setOnCheckedChangeListener(changeChecker);
        togAfter.setOnCheckedChangeListener(changeChecker);

        Log.d("FILENAME: ", fileName);

    }

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            if(isChecked){
                if(compoundButton == togBefore){
                    togDuring.setChecked(false);
                    togAfter.setChecked(false);

                    webView.loadUrl("file:///android_asset/" + fileName + "before.html");

                }

                if(compoundButton == togDuring){
                    togBefore.setChecked(false);
                    togAfter.setChecked(false);

                    webView.loadUrl("file:///android_asset/" + fileName + "during.html");
                }

                if(compoundButton == togAfter){
                    togBefore.setChecked(false);
                    togDuring.setChecked(false);

                    webView.loadUrl("file:///android_asset/" + fileName + "after.html");
                }
            }
        }
    };

}
