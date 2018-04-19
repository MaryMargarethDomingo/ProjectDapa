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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);
        hospital1 = view.findViewById(R.id.hospital1);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        hospital1.setText(preferences.getString("hospital1", "1. "));
    }

}
