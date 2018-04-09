package com.example.itadmin.projectdapa.survival.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.survival.model.SurvivalBean;
import com.example.itadmin.projectdapa.survival.utility.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class SurvivalFragment extends Fragment {

    private ImageButton imageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.survival_listview, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();

        RecyclerView recyclerView = getView().findViewById(R.id.lvCards);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                if (position % 3 == 0) {
                    return 1;
                } else {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        List<SurvivalBean> cardDataList = new ArrayList<>();
        cardDataList.add(new SurvivalBean("SURVIVAL KIT", R.drawable.survival_kit));
        cardDataList.add(new SurvivalBean("EARTHQUAKE", R.drawable.earthquake));
        cardDataList.add(new SurvivalBean("FLOOD", R.drawable.flood));
        cardDataList.add(new SurvivalBean("TYPHOON", R.drawable.storm));
        cardDataList.add(new SurvivalBean("TSUNAMI", R.drawable.tsunami));
        cardDataList.add(new SurvivalBean("VOLCANIC ERUPTION", R.drawable.volcano));
        RecyclerView.Adapter mAdapter = new CardAdapter(getActivity(), cardDataList, getFragmentManager());
        recyclerView.setAdapter(mAdapter);

    }
}
