package com.example.itadmin.projectdapa.survival;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SurivalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SurvivalRecyclerViewAdapter recyclerViewAdapter;
    private List<SurvivalBean> survivalDis;

    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survival_listview);
        
        survivalDis = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        
        recyclerViewAdapter = new SurvivalRecyclerViewAdapter(survivalDis);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        survList();

        cardView = findViewById(R.id.carView);

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
