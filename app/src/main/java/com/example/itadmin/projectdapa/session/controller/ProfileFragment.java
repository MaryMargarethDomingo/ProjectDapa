package com.example.itadmin.projectdapa.session.controller;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.IOException;
import java.net.URL;

public class ProfileFragment extends Fragment {
    private TextView email;
    private TextView phone;
    private TextView username;
    private ImageView image;
    private TextView editProfile;
    private Button logOut;
    private Button settings;

    private Button savedPlaces;
    private Button savedContacts;

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        username = getView().findViewById(R.id.username);
        email = getView().findViewById(R.id.email);
        phone = getView().findViewById(R.id.number);
        image = getView().findViewById(R.id.profilePic);
        editProfile = getView().findViewById(R.id.editProfile);

        savedPlaces = getView().findViewById(R.id.btnSavedPlaces);
        savedContacts = getView().findViewById(R.id.btnSavedContacts);

        settings = getView().findViewById(R.id.btnSettings);
        logOut = getView().findViewById(R.id.btnLogout);

        username.setText(user.getDisplayName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if(user.getPhotoUrl() != null) {
                try {
                    image.setImageBitmap(BitmapFactory.decodeStream(new URL(user.getPhotoUrl().toString()).openConnection().getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        savedPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment savedPlacesFragment = new SavedPlacesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_id, savedPlacesFragment).addToBackStack(null).commit();

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment editProfileFragment = new EditProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_id, editProfileFragment).addToBackStack(null).commit();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }
}