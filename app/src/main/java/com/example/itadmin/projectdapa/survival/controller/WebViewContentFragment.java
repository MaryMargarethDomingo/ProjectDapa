package com.example.itadmin.projectdapa.survival.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.itadmin.projectdapa.R;

public class WebViewContentFragment extends Fragment {

    WebView webView;
    private static String args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        args = getArguments().getString("filename");

        return inflater.inflate(R.layout.survival_webview_content, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        webView = getView().findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/" + args);
    }
}
