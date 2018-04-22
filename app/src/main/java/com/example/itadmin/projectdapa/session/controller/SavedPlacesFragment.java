package com.example.itadmin.projectdapa.session.controller;


import android.os.Bundle;
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

    private String args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);

        args = getArguments().getString("data");

        hospital1 = view.findViewById(R.id.hospital1);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        hospital1.setText(args);
    }

}
