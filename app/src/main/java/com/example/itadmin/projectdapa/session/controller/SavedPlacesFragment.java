package com.example.itadmin.projectdapa.session.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;

public class SavedPlacesFragment extends Fragment {

    private TextView hospital1;
    private TextView hospital2;
    private TextView hospital3;

    private TextView police1;
    private TextView police2;
    private TextView police3;

    private TextView fire1;
    private TextView fire2;
    private TextView fire3;

    private TextView vet1;
    private TextView vet2;
    private TextView vet3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);

        hospital1 = view.findViewById(R.id.hospital1);
        hospital2 = view.findViewById(R.id.hospital2);
        hospital3 = view.findViewById(R.id.hospital3);

        police1 = view.findViewById(R.id.police1);
        police2 = view.findViewById(R.id.police2);
        police3 = view.findViewById(R.id.police3);

        fire1 = view.findViewById(R.id.fireDept1);
        fire2 = view.findViewById(R.id.fireDept2);
        fire3 = view.findViewById(R.id.fireDept3);

        vet1 = view.findViewById(R.id.vet1);
        vet2 = view.findViewById(R.id.vet2);
        vet3 = view.findViewById(R.id.vet3);
        
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
