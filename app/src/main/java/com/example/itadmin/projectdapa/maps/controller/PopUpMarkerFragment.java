package com.example.itadmin.projectdapa.maps.controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

    TextView placeNameText;
    TextView vicinityText;
    TextView distanceText;

    ImageButton callButton;
    ImageButton directionButton;
    ImageButton saveOfflineButton;

    private SavedPlacesFragment savedPlacesFragment;

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

                savedPlacesFragment = new SavedPlacesFragment();
                Bundle args = new Bundle();
                args.putString("data", MapsFragment.placeName);
                savedPlacesFragment.setArguments(args);

                Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().add(R.id.pagerID, savedPlacesFragment).addToBackStack(null).commit();

                /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                if(preferences.getString("hospital1", "").equals(MapsFragment.placeName + ":" + MapsFragment.vicinity + ":" + MapsFragment.strDistance)){
                    Toast.makeText(getContext(), "Already Saved!", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("hospital1", MapsFragment.placeName + ":" + MapsFragment.vicinity + ":" + MapsFragment.strDistance);
                    editor.commit();
                    
                    Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

    }
}
