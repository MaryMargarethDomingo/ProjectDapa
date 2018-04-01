package com.example.itadmin.projectdapa.session.controller;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ProfileFragment extends Fragment {
    private TextView email;
    private TextView phone;
    private TextView username;
    private ImageView image;
    private Button editProfile;
    private Button logOut;

    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        username = (TextView) getView().findViewById(R.id.username);
        email = (TextView) getView().findViewById(R.id.email);
        phone = (TextView) getView().findViewById(R.id.phone);
        image = getView().findViewById(R.id.profilePic);
        editProfile = getView().findViewById(R.id.editProfile);
        logOut = getView().findViewById(R.id.logOut);


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