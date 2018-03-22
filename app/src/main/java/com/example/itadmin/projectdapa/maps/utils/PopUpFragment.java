package com.example.itadmin.projectdapa.maps.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.maps.MapsFragment;


public class  SlidingListFragment extends Fragment {

    TextView placeNameText;
    TextView vicinityText;
    String placeName = MapsFragment.placeName;
    String vicinity = MapsFragment.vicinity;

    public SlidingListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maps_sliding_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placeNameText = (TextView) getView().findViewById(R.id.placeName);
        vicinityText = (TextView)  getView().findViewById(R.id.vicinity);

        placeNameText.setText(placeName);
        vicinityText.setText(vicinity);


    }

}
