package com.example.itadmin.projectdapa;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GPSTracker gpsTracker = new GPSTracker(this);
        mMap.setMyLocationEnabled(true);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            String stringLongitude = String.valueOf(gpsTracker.longitude);

            LatLng currLocation = new LatLng(Double.parseDouble(stringLatitude), Double.parseDouble(stringLongitude));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 3000, null);

            Toast.makeText(this, "Latitude is : " + stringLatitude + "\nLongitude is : " + stringLongitude, Toast.LENGTH_LONG).show();
//            String country = gpsTracker.getCountryName(this);
//            String city = gpsTracker.getLocality(this);
//            String postalCode = gpsTracker.getPostalCode(this);


            String addressLine = gpsTracker.getAddressLine(this);

        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //gpsTracker.showSettingsAlert();
        }
    }
}
