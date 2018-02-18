package com.example.itadmin.projectdapa.survival;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.R;

import java.util.ArrayList;
import java.util.List;

public class SurvivalFragment extends Fragment {

    private RecyclerView recyclerView;
    private SurvivalRecyclerViewAdapter recyclerViewAdapter;
    private List<SurvivalBean> survivalDis;

    private CardView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.survival_listview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        
        survivalDis = new ArrayList<>();
        recyclerView = getView().findViewById(R.id.recyclerView);
        
        recyclerViewAdapter = new SurvivalRecyclerViewAdapter(survivalDis, getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        survList();

        cardView = getView().findViewById(R.id.carView);

    }

    private void survList(){

        SurvivalBean _survivalDis = new SurvivalBean("SURVIVAL KIT",R.drawable.survkit);
        survivalDis.add(_survivalDis);

        _survivalDis = new SurvivalBean("Earthquake",R.drawable.earthquake);
        survivalDis.add(0, _survivalDis);

        _survivalDis = new SurvivalBean("Typhoon",R.drawable.rain);
        survivalDis.add(1, _survivalDis);

        _survivalDis = new SurvivalBean("Flood",R.drawable.flood);
        survivalDis.add(2, _survivalDis);

        _survivalDis = new SurvivalBean("Tsunami",R.drawable.tsunami);
        survivalDis.add(3, _survivalDis);
        
        _survivalDis = new SurvivalBean("Volcanic Eruption",R.drawable.volcanic);
        survivalDis.add(4, _survivalDis);

        recyclerViewAdapter.notifyDataSetChanged();
    }
}
