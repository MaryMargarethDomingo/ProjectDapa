package com.example.itadmin.projectdapa.session.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.session.utility.CustomPlacesAdapter;
import com.example.itadmin.projectdapa.session.utility.NonScrollableListView;

import java.util.ArrayList;

public class SavedPlacesFragment extends Fragment {

    private NonScrollableListView lvHospitals;
    private NonScrollableListView lvPolice;
    private NonScrollableListView lvFireDepartment;
    private NonScrollableListView lvVeterinary;

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        lvHospitals = view.findViewById(R.id.lvHospital);
        lvPolice = view.findViewById(R.id.lvPolice);
        lvFireDepartment = view.findViewById(R.id.lvFireDept);
        lvVeterinary = view.findViewById(R.id.lvVeterinary);

        return view;

    }

    private String[] hosptals, police, fire, vet;

    @Override
    public void onStart(){
        super.onStart();

        hosptals = sharedPreferences.getString("savedHospital", "No saved place").split(" . ");
        police = sharedPreferences.getString("savedPolice", "No saved place").split(" . ");
        fire = sharedPreferences.getString("savedFire", "No saved place").split(" . ");
        vet = sharedPreferences.getString("savedVet", "No saved place").split(" . ");
        ArrayList hospitalList = new ArrayList();
        ArrayList policeList = new ArrayList();
        ArrayList fireDeptList = new ArrayList();
        ArrayList veterinaryList = new ArrayList();


        if(hosptals.length == 1){
            hospitalList.add("1. " + hosptals[0]);


        }else if (hosptals.length == 2){
            hospitalList.add("1. " + hosptals[0]);
            hospitalList.add("2. " + hosptals[1]);

        }else {
            hospitalList.add("1. " + hosptals[0]);
            hospitalList.add("2. " + hosptals[1]);
            hospitalList.add("3. " + hosptals[2]);

        }

        if(police.length == 1){
            policeList.add("1. " + police[0]);

        }else if(police.length == 2){
            policeList.add("1. " + police[0]);
            policeList.add("2. " + police[1]);

        }else{
            policeList.add("1. " + police[0]);
            policeList.add("2. " + police[1]);
            policeList.add("3. " + police[2]);
        }

        if(fire.length == 1){
            fireDeptList.add("1. " + fire[0]);

        }else if(fire.length == 2){
            fireDeptList.add("1. " + fire[0]);
            fireDeptList.add("2. " + fire[1]);

        }else{
            fireDeptList.add("1. " + fire[0]);
            fireDeptList.add("2. " + fire[1]);
            fireDeptList.add("3. " + fire[2]);
        }

        if(vet.length == 1){
            veterinaryList.add("1. " + vet[0]);

        }else if(vet.length == 2){
            veterinaryList.add("1. " + vet[0]);
            veterinaryList.add("2. " + vet[1]);

        }else{
            veterinaryList.add("1. " + vet[0]);
            veterinaryList.add("2. " + vet[1]);
            veterinaryList.add("3. " + vet[2]);
        }

        CustomPlacesAdapter hospitalAdapter = new CustomPlacesAdapter(getActivity(), hospitalList);
        CustomPlacesAdapter policeAdapter = new CustomPlacesAdapter(getActivity(), policeList);
        CustomPlacesAdapter fireDeptAdapter = new CustomPlacesAdapter(getActivity(), fireDeptList);
        CustomPlacesAdapter veterinaryAdapter = new CustomPlacesAdapter(getActivity(),veterinaryList);

        lvHospitals.setAdapter(hospitalAdapter);
        lvPolice.setAdapter(policeAdapter);
        lvFireDepartment.setAdapter(fireDeptAdapter);
        lvVeterinary.setAdapter(veterinaryAdapter);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());

                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));

                openItem.setWidth(170);

                openItem.setIcon(R.drawable.ic_magnify_black_48dp);

                openItem.setTitleSize(18);

                openItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(openItem);


                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());

                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));

                deleteItem.setWidth(170);

                deleteItem.setIcon(R.drawable.ic_magnify_black_48dp);

                menu.addMenuItem(deleteItem);
            }
        };

        lvHospitals.setMenuCreator(creator);
        lvPolice.setMenuCreator(creator);
        lvFireDepartment.setMenuCreator(creator);
        lvVeterinary.setMenuCreator(creator);

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
