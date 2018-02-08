package com.example.itadmin.projectdapa.maps;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.itadmin.projectdapa.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    GoogleApiClient client;
    LocationRequest locationRequest;
    Location lastLocation;
    Marker currentLocationMarker;
    static int PROXIMITY_RADIUS = 15 * 1000;
    static double latitude;
    static double longitude;
    static String type;

    Bundle bundle = new Bundle();

    Button btnGo;
    Button btnListView;

    private ToggleButton togHospital;
    private ToggleButton togPolice;
    private ToggleButton togFire;
    private ToggleButton togVet;

    static double endMarkerLat;
    static double endMarkerLng;

    public static final int REQUEST_LOCATION_CODE = 99;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_maps, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        togHospital = getView().findViewById(R.id.togHospital);
        togPolice = getView().findViewById(R.id.togPolice);
        togFire = getView().findViewById(R.id.togFire);
        togVet = getView().findViewById(R.id.togVet);

        togHospital.setOnCheckedChangeListener(changeChecker);
        togPolice.setOnCheckedChangeListener(changeChecker);
        togFire.setOnCheckedChangeListener(changeChecker);
        togVet.setOnCheckedChangeListener(changeChecker);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        bundle = this.getArguments();

        btnGo = getView().findViewById(R.id.btnGo);
        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Object dataTransfer[] = new Object[3];
                String url = getDirectionsUrl();
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = new LatLng(endMarkerLat, endMarkerLng);

                getDirectionsData.execute(dataTransfer);

                getDistance();
            }
        });

        btnListView = getView().findViewById(R.id.btnListView);
        btnListView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

                bundle.putString("jsonData", prefs.getString("jsonData", ""));

                ListViewFragment listViewFragment = new ListViewFragment();
                listViewFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.content_id, listViewFragment).commit();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // i grant you my permission
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(client == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        //permission denied
                    }else{
                        Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                    }

                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(14.599512, 120.984219), 6));

        googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        getContext(), R.raw.style_json));
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

        mMap.setOnMarkerClickListener(this);
    }

    protected synchronized void buildGoogleApiClient(){
        client = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastLocation = location;

        Log.d("MapsActivity-Latitude","Latitude: " + latitude);
        Log.d("MapsActivity-Longitude","Longitude: " + longitude);

        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        currentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(2));

        if(client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//-------------------------------------------------- Check Permissions - Code --------------------------------------------------

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION )){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            return false;

        }else{
            return true;
        }
    }

//-------------------------------------------------- Places, Directions, Distance - Code --------------------------------------------------

    public void showPins(){
        Object dataTransfer[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        mMap.clear();
        String url1 = getPlacesURL();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url1;

        getNearbyPlaces.execute(dataTransfer);
        Toast.makeText(getActivity(), "Showing nearby " + type, Toast.LENGTH_SHORT).show();

    }

   /*GOOGLE PLACES TYPES:
    hospital
    doctor
    dentist
    veterinary_care
    police
    fire_station*/

    public static String getPlacesURL(){

        String googlePlaceUrl = ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                + "location=" + latitude + "," + longitude
                + "&radius=" + PROXIMITY_RADIUS
                + "&type=" + type
                //+ "&hasNextPage=true&nextPage()=true"
                + "&key="+"AIzaSyAI8AWBOjy-m-t281QDrwov3r2KImzc__A");

        Log.d("PLACES URL","URL: " + googlePlaceUrl);

        return googlePlaceUrl;
    }

    public void getDistance(){
        float results[] = new float[10];
        Location.distanceBetween(latitude, longitude, endMarkerLat, endMarkerLng, results);

        DecimalFormat distanceFormat = new DecimalFormat("#.##");
        String strDistance = distanceFormat.format(results[0]/1000);

        Toast.makeText(getActivity(), "Distance: " + strDistance + "KM", Toast.LENGTH_LONG).show();

        //return results;
    }

    public static String getDirectionsUrl(){

        String googleDirectionsUrl = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + latitude + "," + longitude
                + "&destination=" + endMarkerLat + "," + endMarkerLng
                + "&keys=AIzaSyAr6glfw4qPY6dkrrZoLWg0hDT9DCpAXkg";

        Log.d("DIRECTIONS URL: ", googleDirectionsUrl);

        return googleDirectionsUrl;
    }

//-------------------------------------------------- ToggleButton - Code --------------------------------------------------

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            if(compoundButton == togHospital){
                mMap.clear();
                type = "hospital";
                showPins();

                togPolice.setChecked(false);
                togFire.setChecked(false);
                togVet.setChecked(false);

            }else{
                mMap.clear();
                type = null;
            }

            if(compoundButton == togPolice){
                mMap.clear();
                type = "police";
                showPins();

                togHospital.setChecked(false);
                togFire.setChecked(false);
                togVet.setChecked(false);

            }else{
                mMap.clear();
                type = null;
            }

            if(compoundButton == togFire){
                mMap.clear();
                type = "fire_station";
                showPins();

                togHospital.setChecked(false);
                togPolice.setChecked(false);
                togVet.setChecked(false);

            }else{
                mMap.clear();
                type = null;
            }

            if(compoundButton == togVet){
                mMap.clear();
                type = "veterinary_care";
                showPins();

                togHospital.setChecked(false);
                togPolice.setChecked(false);
                togFire.setChecked(false);

            }else{
                mMap.clear();
                type = null;
            }

        }
    };

//-------------------------------------------------- Marker click - METHODS --------------------------------------------------

    @Override
    public boolean onMarkerClick(Marker marker) {

        endMarkerLat = marker.getPosition().latitude;
        endMarkerLng = marker.getPosition().longitude;

        return false;
    }

}
