package com.example.itadmin.projectdapa.survival;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
        
        recyclerViewAdapter = new SurvivalRecyclerViewAdapter(survivalDis);
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
        survivalDis.add(_survivalDis);

        _survivalDis = new SurvivalBean("Rainstorm",R.drawable.rain);
        survivalDis.add(_survivalDis);

        _survivalDis = new SurvivalBean("Flood",R.drawable.flood);
        survivalDis.add(_survivalDis);

        _survivalDis = new SurvivalBean("Tsunami",R.drawable.tsunami);
        survivalDis.add(_survivalDis);
        
        _survivalDis = new SurvivalBean("Volcanic Eruption",R.drawable.volcanic);
        survivalDis.add(_survivalDis);

        recyclerViewAdapter.notifyDataSetChanged();
    }
}
