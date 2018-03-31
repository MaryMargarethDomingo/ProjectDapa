package com.example.itadmin.projectdapa.survival.controller.survivalWebPages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.itadmin.projectdapa.R;

public class WebViewTsunamiFragment extends Fragment {

    WebView webView;

    String fileName = "";

    public WebViewTsunamiFragment(){}

    @SuppressLint("ValidFragment")
    public WebViewTsunamiFragment(int position){
        switch (position){
            case 0:
                fileName = "tsunami_before.html";
                break;

            case 1:
                fileName = "tsunami_during.html";
                break;

            case 3:
                fileName = "tsunami_after.html";
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
