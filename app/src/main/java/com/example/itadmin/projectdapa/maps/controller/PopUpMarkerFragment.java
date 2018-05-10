package com.example.itadmin.projectdapa.maps.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.session.controller.SavedPlacesFragment;

public class PopUpMarkerFragment extends BottomSheetDialogFragment {

    private TextView placeNameText;
    private TextView vicinityText;
    private TextView distanceText;

    private ImageButton callButton;
    private ImageButton directionButton;
    private ImageButton saveOfflineButton;

    private static String savedHospital = "";
    private static String savedPolice = "";
    private static String savedFire = "";
    private static String savedVet = "";

    private String args;

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
        distanceText.setText(MapsFragment.strDistance + " KM away");

        args = getArguments().getString("place");

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: API number here"));

                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
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

                if(args.equals("hospital")){
                    savedHospital.concat(placeNameText + ", " + vicinityText + ". ");

                }else if(args.equals("police")){
                    savedPolice.concat(placeNameText + ", " + vicinityText + ". ");

                }else if(args.equals("fire_station")){
                    savedFire.concat(placeNameText + ", " + vicinityText + ". ");

                }else if(args.equals("veterinary_care")){
                    savedVet.concat(placeNameText + ", " + vicinityText + ". ");

                }

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            }
        });

    }
}
