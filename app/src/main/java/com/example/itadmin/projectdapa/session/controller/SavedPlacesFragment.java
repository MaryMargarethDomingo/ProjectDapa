package com.example.itadmin.projectdapa.session.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

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

    private String[] hosptals, police, fire, vet;

    @Override
    public void onStart() {
        super.onStart();

        hosptals = sharedPreferences.getString("savedHospital", "No saved place").split(".");
        police = sharedPreferences.getString("savedPolice", "No saved place").split(".");
        fire = sharedPreferences.getString("savedFire", "No saved place").split(".");
        vet = sharedPreferences.getString("savedVet", "No saved place").split(".");

        hospital1.setText(hosptals[0]);
        hospital2.setText(hosptals[1]);
        hospital3.setText(hosptals[2]);

        police1.setText(police[0]);
        police2.setText(police[1]);
        police3.setText(police[2]);

        fire1.setText(fire[0]);
        fire2.setText(fire[1]);
        fire3.setText(fire[2]);

        vet1.setText(vet[0]);
        vet2.setText(vet[1]);
        vet3.setText(vet[2]);

    }

}
