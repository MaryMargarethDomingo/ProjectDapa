package com.example.itadmin.projectdapa.maps.controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.session.controller.SavedPlacesFragment;

public class PopUpReportMarkerFragment extends BottomSheetDialogFragment {

    TextView description;
    TextView distanceText;
    TextView reporter;

    ImageButton likeButton;

    public PopUpReportMarkerFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.maps_popup_report_marker_layout, container, false);

        description = view.findViewById(R.id.reportDescription);
        distanceText = view.findViewById(R.id.reportDistance);
        reporter = view.findViewById(R.id.reporter);
        likeButton = view.findViewById(R.id.likeButton);


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
