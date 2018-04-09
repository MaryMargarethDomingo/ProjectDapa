package com.example.itadmin.projectdapa.maps.controller;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;

import java.util.Map;

public class PopUpMarkerFragment extends BottomSheetDialogFragment {

    TextView placeNameText;
    TextView vicinityText;
    TextView distanceText;

    ImageButton callButton;
    ImageButton directionButton;
    ImageButton saveOfflineButton;

    public PopUpMarkerFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_popup_marker_layout, container, false);

        callButton = view.findViewById(R.id.popUpCall);
        directionButton = view.findViewById(R.id.popupDirections);
        saveOfflineButton = view.findViewById(R.id.popupSave);

        placeNameText = view.findViewById(R.id.placeName);
        vicinityText = view.findViewById(R.id.vicinity);
        distanceText = view.findViewById(R.id.distance);

        placeNameText.setText(MapsFragment.placeName);
        vicinityText.setText(MapsFragment.vicinity);
        MapsFragment.getDistance();
        distanceText.setText(MapsFragment.strDistance + " KM");

        Log.d("POPUP MARKER DATA: ", MapsFragment.placeName);
        Log.d("POPUP MARKER DATA: ", MapsFragment.vicinity);
        Log.d("POPUP MARKER DATA: ", MapsFragment.strDistance);


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "CLICKY CLICKY CALL", Toast.LENGTH_LONG).show();
            }
        });

        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsFragment.showDirections();
            }
        });

        saveOfflineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "CLICKY CLICKY SAVE", Toast.LENGTH_LONG).show();
            }
        });

    }
}
