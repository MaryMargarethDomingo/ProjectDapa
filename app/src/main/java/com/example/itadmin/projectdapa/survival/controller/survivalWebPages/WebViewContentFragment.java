package com.example.itadmin.projectdapa.survival.controller.survivalWebPages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.itadmin.projectdapa.R;

public class WebViewContentFragment extends Fragment {

    WebView webView;
<<<<<<< HEAD:app/src/main/java/com/example/itadmin/projectdapa/survival/controller/survivalWebPages/WebViewFloodFragment.java

    String fileName = "";

    public WebViewFloodFragment(){}

    @SuppressLint("ValidFragment")
    public WebViewFloodFragment(int position){
        switch (position){
            case 0:
                fileName = "flood_before.html";
                break;

            case 1:
                fileName = "flood_during.html";
                break;

            case 2:
                fileName = "flood_after.html";
                break;
        }
    }
=======
    private static String args;
>>>>>>> e129ece1ad1532fc14b89ca1f4a988e7c5b2b45b:app/src/main/java/com/example/itadmin/projectdapa/survival/controller/survivalWebPages/WebViewContentFragment.java

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        args = getArguments().getString("state");

        return inflater.inflate(R.layout.survival_webview_content, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        webView = getView().findViewById(R.id.webview1);
<<<<<<< HEAD:app/src/main/java/com/example/itadmin/projectdapa/survival/controller/survivalWebPages/WebViewFloodFragment.java
        webView.loadUrl("file:///android_asset/" + fileName);
=======
        webView.loadUrl("file:///android_asset/" + args);

>>>>>>> e129ece1ad1532fc14b89ca1f4a988e7c5b2b45b:app/src/main/java/com/example/itadmin/projectdapa/survival/controller/survivalWebPages/WebViewContentFragment.java
    }
}
