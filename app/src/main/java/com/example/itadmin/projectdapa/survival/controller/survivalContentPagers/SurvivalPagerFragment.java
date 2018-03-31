package com.example.itadmin.projectdapa.survival.controller.survivalContentPagers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.survival.controller.survivalWebPages.WebViewContentFragment;

public class SurvivalPagerFragment extends Fragment {

    private Button btnBefore;
    private Button btnDuring;
    private Button btnAfter;

    private String fileName = "";

    private Fragment webViewContent = new WebViewContentFragment();

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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("state", fileName + "before.html");

                webViewContent.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_id, webViewContent);
                fragmentTransaction.commit();
            }
        });

        btnDuring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("state", fileName + "during.html");

                webViewContent.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_id, webViewContent);
                fragmentTransaction.commit();
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("state", fileName + "after.html");

                webViewContent.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_id, webViewContent);
                fragmentTransaction.commit();
            }
        });

    }

}
