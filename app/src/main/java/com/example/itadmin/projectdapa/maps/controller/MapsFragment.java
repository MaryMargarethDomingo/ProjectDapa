package com.example.itadmin.projectdapa.maps.controller;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.maps.model.Reports;
import com.example.itadmin.projectdapa.maps.utility.GetDirectionsData;
import com.example.itadmin.projectdapa.maps.utility.GetNearbyPlaces;
import com.example.itadmin.projectdapa.session.controller.SavedPlacesFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener{

    private static GoogleMap mMap;
    GoogleApiClient client;
    LocationRequest locationRequest;
    Location lastLocation;
    Marker currentLocationMarker;
    static int PROXIMITY_RADIUS = 2 * 1000;
    public static double latitude = 0;
    public static double longitude = 0;
    public static String type;

    private Bundle bundle = new Bundle();

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static double endMarkerLat;
    private static double endMarkerLng;
    private DatabaseReference database;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FloatingActionButton reportFab;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;
    private Animation show_fab1_menu;
    private Animation hide_fab1_menu;
    private Animation show_fab2_menu;
    private Animation hide_fab2_menu;
    private Animation show_fab3_menu;
    private Animation hide_fab3_menu;
    private static BottomSheetDialogFragment bottomSheetDialogFragment = new PopUpMarkerFragment();
    private static BottomSheetDialogFragment bottomSheetReportDialogFragment = new PopUpReportMarkerFragment();
    private static ArrayList<DataSnapshot> reports = new ArrayList<>();

    public static final int REQUEST_LOCATION_CODE = 99;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        database = FirebaseDatabase.getInstance().getReference("reports");

        return inflater.inflate(R.layout.maps_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        show_fab1_menu = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.reportfab1_show);
        hide_fab1_menu = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.reportfab1_hide);
        show_fab2_menu = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.reportfab2_show);
        hide_fab2_menu = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.reportfab2_hide);
        show_fab3_menu = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.reportfab3_show);
        hide_fab3_menu = AnimationUtils.loadAnimation(getActivity().getApplication(), R.anim.reportfab3_hide);

    }

        private boolean justClicked = false;

    @Override
    public void onStart(){
        super.onStart();

        togHospital = getView().findViewById(R.id.togHospital);
        togPolice = getView().findViewById(R.id.togPolice);
        togFire = getView().findViewById(R.id.togFire);
        togVet = getView().findViewById(R.id.togVet);
        togReports = getView().findViewById(R.id.togReports);
        reportFab = getView().findViewById(R.id.floatingActionButton);
        fab1 = getView().findViewById(R.id.fab_1);
        fab2 = getView().findViewById(R.id.fab_2);
        fab3 = getView().findViewById(R.id.fab_3);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();

        togHospital.setOnCheckedChangeListener(changeChecker);
        togPolice.setOnCheckedChangeListener(changeChecker);
        togFire.setOnCheckedChangeListener(changeChecker);
        togVet.setOnCheckedChangeListener(changeChecker);
        togReports.setOnCheckedChangeListener(changeChecker);

        reportFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFabMenuDisplayed){
                    displayFabMenu();
                }else{
                    hideFabMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!justClicked){
                    if(latitude != 0 || longitude != 0){
                        Reports reports = new Reports(latitude, longitude, user.getEmail(), "Earthquake");
                        database.child(database.push().getKey()).setValue(reports);

                        Toast.makeText(getContext(), "Report Successful!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "You've just reported! Please try again in few seconds", Toast.LENGTH_SHORT).show();
                }

                justClicked = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        justClicked = false;
                    }
                }, 5000);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!justClicked){
                    if(latitude != 0 || longitude != 0){
                        Reports reports = new Reports(latitude, longitude, user.getEmail(), "Storm");
                        database.child(database.push().getKey()).setValue(reports);

                        Toast.makeText(getContext(), "Report Successful!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "You've just reported! Please try again in few seconds", Toast.LENGTH_SHORT).show();
                }

                justClicked = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        justClicked = false;
                    }
                }, 5000);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!justClicked){
                    if(latitude != 0 || longitude != 0){
                        Reports reports = new Reports(latitude, longitude, user.getEmail(), "Fire");
                        database.child(database.push().getKey()).setValue(reports);

                        Toast.makeText(getContext(), "Report Successful!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "You've just reported! Please try again in few seconds", Toast.LENGTH_SHORT).show();
                }

                justClicked = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        justClicked = false;
                    }
                }, 5000);
            }
        });


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        bundle = this.getArguments();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(14.599512, 120.984219), 6));

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

        mMap.setOnMarkerClickListener(this);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    reports.add(dsp);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(Double.parseDouble(dsp.child("latitude").getValue().toString()),
                            Double.parseDouble(dsp.child("longitude").getValue().toString())));
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
                    mMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected synchronized void buildGoogleApiClient(){
        client = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    public String currentCity(double lat, double lng){
        String currentCity = "";

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        try{
            addressList = geocoder.getFromLocation(lat, lng, 1);

            if(addressList.size() > 0){
                currentCity = addressList.get(0).getLocality();

                editor.putString("currCity", currentCity);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return currentCity;
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
        currentCity(latitude, longitude);

        //save lat lng for auto set of location of weather
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.edit().putFloat("lat", (float) latitude).putFloat("lng", (float) longitude).commit();

        /*MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

        currentLocationMarker = mMap.addMarker(markerOptions);*/

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        mMap.animateCamera(cameraUpdate);

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
                        mMap.setPadding(0,100,0,0);
                        //permission denied
                    }else{
                        Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_LONG).show();
                    }

                }
        }
    }

//-------------------------------------------------- Places, Directions, Distance - Code --------------------------------------------------

    public static String strDistance;
    public static String estDuration;

    public static void showPins(){
        Object dataTransfer[] = new Object[3];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        mMap.clear();
        String url1 = getPlacesURL();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url1;
        dataTransfer[2] = type;

        getNearbyPlaces.execute(dataTransfer);
        //Toast.makeText(getActivity(), "Showing nearby " + type, Toast.LENGTH_SHORT).show();

    }

    public static void showDirections(){
        GetDirectionsData getDirectionsData = new GetDirectionsData();
        Object dataTransferDuration[];

        dataTransferDuration = new Object[2];
        String durationUrl = getDirectionsUrl();
        dataTransferDuration[0] = mMap;
        dataTransferDuration[1] = durationUrl;

        getDirectionsData.execute(dataTransferDuration);
        getDistance();

        showPins();
        mMap.clear();
        bottomSheetDialogFragment.dismiss();
        
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

    public static void getDistance(){
        float results[] = new float[10];
        Location.distanceBetween(latitude, longitude, endMarkerLat, endMarkerLng, results);

        DecimalFormat distanceFormat = new DecimalFormat("#.##");
        strDistance = distanceFormat.format(results[0]/1000);
        estDuration = GetDirectionsData.duration;

        /*Toast.makeText(this, "Distance: " + strDistance + "KM" +
                "\n" + "Estimated duration: " + GetDirectionsData.duration , Toast.LENGTH_LONG).show();

        //return results;*/
    }

    public static String getDirectionsUrl(){

        String googleDirectionsUrl = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + latitude + "," + longitude
                + "&destination=" + endMarkerLat + "," + endMarkerLng
                + "&keys=AIzaSyAr6glfw4qPY6dkrrZoLWg0hDT9DCpAXkg";

        Log.d("DIRECTIONS URL: ", "URL" + googleDirectionsUrl);

        return googleDirectionsUrl;
    }


//-------------------------------------------------- ToggleButton - Code --------------------------------------------------

    private ToggleButton togHospital;
    private ToggleButton togPolice;
    private ToggleButton togFire;
    private ToggleButton togVet;
    private ToggleButton togReports;


    SavedPlacesFragment savedPlacesFragment = new SavedPlacesFragment();

    CompoundButton.OnCheckedChangeListener changeChecker = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            if (isChecked) {
                if (compoundButton == togHospital) {

                    togPolice.setChecked(false);
                    togFire.setChecked(false);
                    togVet.setChecked(false);
                    togReports.setChecked(false);

                    mMap.clear();
                    type = "hospital";
                    showPins();

                    endMarkerLat = 0;
                    endMarkerLng = 0;

                    Toast.makeText(getActivity(), "Showing nearby hospitals", Toast.LENGTH_LONG).show();

                    editor.putString("place", type).commit();

/*                    for(DataSnapshot report: reports){
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(Double.parseDouble(report.child("latitude").getValue().toString()),
                                Double.parseDouble(report.child("longitude").getValue().toString())));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
                        mMap.addMarker(markerOptions);
                    }*/
                }

                if (compoundButton == togPolice) {

                    togHospital.setChecked(false);
                    togFire.setChecked(false);
                    togVet.setChecked(false);
                    togReports.setChecked(false);

                    mMap.clear();
                    type = "police";
                    showPins();

                    endMarkerLat = 0;
                    endMarkerLng = 0;

                    Toast.makeText(getActivity(), "Showing nearby police stations", Toast.LENGTH_LONG).show();

                    editor.putString("place", type).commit();

/*                    for(DataSnapshot report: reports){
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(Double.parseDouble(report.child("latitude").getValue().toString()),
                                Double.parseDouble(report.child("longitude").getValue().toString())));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
                        mMap.addMarker(markerOptions);
                    }*/

                }

                if (compoundButton == togFire) {

                    togHospital.setChecked(false);
                    togPolice.setChecked(false);
                    togVet.setChecked(false);
                    togReports.setChecked(false);

                    mMap.clear();
                    type = "fire_station";
                    showPins();

                    endMarkerLat = 0;
                    endMarkerLng = 0;

                    Toast.makeText(getActivity(), "Showing nearby fire stations", Toast.LENGTH_LONG).show();

                    editor.putString("place", type).commit();

/*                    for(DataSnapshot report: reports){
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(Double.parseDouble(report.child("latitude").getValue().toString()),
                                Double.parseDouble(report.child("longitude").getValue().toString())));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
                        mMap.addMarker(markerOptions);
                    }*/

                }

                if (compoundButton == togVet) {

                    togHospital.setChecked(false);
                    togPolice.setChecked(false);
                    togFire.setChecked(false);
                    togReports.setChecked(false);

                    mMap.clear();
                    type = "veterinary_care";
                    showPins();

                    endMarkerLat = 0;
                    endMarkerLng = 0;

                    Toast.makeText(getActivity(), "Showing nearby veterinary clinics", Toast.LENGTH_LONG).show();

                    editor.putString("place", type).commit();
/*
                    for(DataSnapshot report: reports){
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(Double.parseDouble(report.child("latitude").getValue().toString()),
                                Double.parseDouble(report.child("longitude").getValue().toString())));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
                        mMap.addMarker(markerOptions);
                    }*/

                }

                if (compoundButton == togReports){
                    togHospital.setChecked(false);
                    togPolice.setChecked(false);
                    togFire.setChecked(false);
                    togVet.setChecked(false);

                    mMap.clear();

                    for(DataSnapshot report: reports){
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(Double.parseDouble(report.child("latitude").getValue().toString()),
                                Double.parseDouble(report.child("longitude").getValue().toString())));
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
                        mMap.addMarker(markerOptions);
                    }

                    Toast.makeText(getActivity(), "Showing reports", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

//-------------------------------------------------- Marker click - METHODS --------------------------------------------------
    public static String placeName;
    public static String vicinity;

    @Override
    public boolean onMarkerClick(Marker marker) {

        endMarkerLat = marker.getPosition().latitude;
        endMarkerLng = marker.getPosition().longitude;

        if(marker.getTitle() != null){
            String[] titleArr = marker.getTitle().split(" : ");

            placeName = titleArr[0];
            vicinity = titleArr[1];

            bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        }else{
            bottomSheetReportDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetReportDialogFragment.getTag());
        }

        return false;
    }


    private boolean isFabMenuDisplayed = false;
    private void displayFabMenu(){
        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams1.rightMargin += (int) (fab1.getWidth() * 1.75);
        layoutParams1.bottomMargin += (int) (fab1.getHeight() * -0.25);

        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.25);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.25);

        layoutParams3.rightMargin += (int) (fab3.getWidth() * -0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.75);

        fab1.setLayoutParams(layoutParams1);
        fab1.startAnimation(show_fab1_menu);
        fab1.setClickable(true);

        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab2_menu);
        fab2.setClickable(true);

        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab3_menu);
        fab3.setClickable(true);

        isFabMenuDisplayed = true;
    }

    private void hideFabMenu(){
        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams1.rightMargin -= (int) (fab1.getWidth() * 1.75);
        layoutParams1.bottomMargin -= (int) (fab1.getHeight() * -0.25);
        fab1.setLayoutParams(layoutParams1);
        fab1.startAnimation(hide_fab1_menu);
        fab1.setClickable(false);

        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.25);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.25);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab2_menu);
        fab2.setClickable(false);

        layoutParams3.rightMargin -= (int) (fab3.getWidth() * -0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.75);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab3_menu);
        fab3.setClickable(false);

        isFabMenuDisplayed = false;
    }

}
