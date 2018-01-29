package com.example.itadmin.projectdapa.maps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.itadmin.projectdapa.R;

public class ListFragment extends Fragment {

    private Button btnHospital;
    private Button btnPolice;
    private Button btnFire;
    private Button btnVeterinary;
    private String type;
    private Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        btnHospital = (Button) getView().findViewById(R.id.btnHospital);
        btnPolice = (Button) getView().findViewById(R.id.btnPolice);
        btnFire = (Button) getView().findViewById(R.id.btnFire );
        btnVeterinary = (Button) getView().findViewById(R.id.btnVeterinary);

        btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "hospital";
                bundle.putString("place" , type);

                MapsFragment newFragment = new MapsFragment();
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack if needed
                transaction.replace(R.id.listFragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        btnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "fire_station";
                bundle.putString("place" , type);

                MapsFragment newFragment = new MapsFragment();
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack if needed
                transaction.replace(R.id.listFragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "police";
                bundle.putString("place" , type);

                MapsFragment newFragment = new MapsFragment();
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack if needed
                transaction.replace(R.id.listFragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        btnVeterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "veterinary_care";
                bundle.putString("place" , type);

                MapsFragment newFragment = new MapsFragment();
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack if needed
                transaction.replace(R.id.listFragment, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }
}
