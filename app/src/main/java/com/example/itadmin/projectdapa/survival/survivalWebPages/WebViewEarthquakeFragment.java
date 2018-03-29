package com.example.itadmin.projectdapa.survival.survivalWebPages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.itadmin.projectdapa.R;

public class WebViewEarthquakeFragment extends Fragment {

    WebView webView;

    String fileName = "";

    public WebViewEarthquakeFragment(){}

    /*public WebViewEarthquakeFragment(int position){
        switch (position){
            case 0:
                fileName = "earthquake_before.html";
                break;

            case 1:
                fileName = "earthquake_during.html";
                break;

            case 3:
                fileName = "earthquake_after.html";
                break;
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.survival_webview_content, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        webView = getView().findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/" + fileName);

    }
}
