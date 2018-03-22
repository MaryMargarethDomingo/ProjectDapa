package com.example.itadmin.projectdapa.maps.controller;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.itadmin.projectdapa.R;


public class PopUpMarkerFragment extends BottomSheetDialogFragment {

    TextView placeNameText;
    TextView vicinityText;

    public PopUpMarkerFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_popup_marker_layout, container, false);

        placeNameText = (TextView) view.findViewById(R.id.placeName);
        vicinityText = (TextView)  view.findViewById(R.id.vicinity);

        placeNameText.setText(MapsFragment.placeName);
        vicinityText.setText(MapsFragment.vicinity);

        return view;
    }
}
