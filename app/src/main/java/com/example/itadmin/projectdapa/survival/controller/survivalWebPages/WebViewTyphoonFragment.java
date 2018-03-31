package com.example.itadmin.projectdapa.survival.controller.survivalWebPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.itadmin.projectdapa.R;

public class WebViewTyphoonFragment extends Fragment {

    WebView webView;

    String fileName = "";

    public WebViewTyphoonFragment(){}

    @SuppressLint("ValidFragment")
    public WebViewTyphoonFragment(int position){
        switch (position){
            case 0:
                fileName = "typhoon_before.html";
                break;

            case 1:
                fileName = "typhoon_during.html";
                break;

            case 2:
                fileName = "typhoon_after.html";
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
        webView.loadUrl("file:///android_asset/" + fileName);

    }
}
