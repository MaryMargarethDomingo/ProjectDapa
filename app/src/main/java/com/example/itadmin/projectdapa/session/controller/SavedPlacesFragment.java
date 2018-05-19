package com.example.itadmin.projectdapa.session.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    private ImageButton hospCall1, hospCall2, hospCall3;
    private ImageButton policeCall1, policeCall2, policeCall3;
    private ImageButton fireCall1, fireCall2, fireCall3;
    private ImageButton vetCall1, vetCall2, vetCall3;

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

        hospCall1 = view.findViewById(R.id.hospCall1);
        hospCall2 = view.findViewById(R.id.hospCall2);
        hospCall3 = view.findViewById(R.id.hospCall3);

        policeCall1 = view.findViewById(R.id.policeCall1);
        policeCall2 = view.findViewById(R.id.policeCall2);
        policeCall3 = view.findViewById(R.id.policeCall3);

        fireCall1 = view.findViewById(R.id.fireCall1);
        fireCall2 = view.findViewById(R.id.fireCall2);
        fireCall3 = view.findViewById(R.id.fireCall3);

        vetCall1 = view.findViewById(R.id.vetCall1);
        vetCall2 = view.findViewById(R.id.vetCall2);
        vetCall3 = view.findViewById(R.id.vetCall3);

        return view;

    }

    private String[] hosptals, police, fire, vet;

    @Override
    public void onStart() {
        super.onStart();

        hosptals = sharedPreferences.getString("savedHospital", "No saved place").split(" . ");
        police = sharedPreferences.getString("savedPolice", "No saved place").split(" . ");
        fire = sharedPreferences.getString("savedFire", "No saved place").split(" . ");
        vet = sharedPreferences.getString("savedVet", "No saved place").split(" . ");

        if(hosptals.length == 1){
            hospital1.setText("1. " + hosptals[0]);

        }else if (hosptals.length == 2){
            hospital1.setText("1. " + hosptals[0]);
            hospital2.setText("2. " + hosptals[1]);

        }else {
            hospital1.setText("1. " + hosptals[0]);
            hospital2.setText("2. " + hosptals[1]);
            hospital3.setText("3. " + hosptals[2]);
        }

        if(police.length == 1){
            police1.setText("1. " + police[0]);

        }else if(police.length == 2){
            police1.setText("1. " + police[0]);
            police2.setText("2. " + police[1]);

        }else{
            police1.setText("1. " + police[0]);
            police2.setText("2. " + police[1]);
            police3.setText("3. " + police[2]);
        }

        if(fire.length == 1){
            fire1.setText("1. " + fire[0]);

        }else if(fire.length == 2){
            fire1.setText("1. " + fire[0]);
            fire2.setText("2. " + fire[1]);

        }else{
            fire1.setText("1. " + fire[0]);
            fire2.setText("2. " + fire[1]);
            fire3.setText("3. " + fire[2]);
        }

        if(vet.length == 1){
            vet1.setText("1. " + vet[0]);

        }else if(vet.length == 2){
            vet1.setText("1. " + vet[0]);
            vet2.setText("2. " + vet[1]);

        }else{
            vet1.setText("1. " + vet[0]);
            vet2.setText("2. " + vet[1]);
            vet3.setText("3. " + vet[2]);
        }

    }

    private void call(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel: API number here"));

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

}
