package com.example.itadmin.projectdapa.maps;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String googlePlacesData1;
    //private String googlePlacesData2;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {

        String url1;

        mMap = (GoogleMap) objects[0];
        url1 = (String) objects[1];

        DownloadURL downloadURL = new DownloadURL();

        try {
            googlePlacesData1 = downloadURL.readURL(url1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("GOOGLE PLACES DATA", googlePlacesData1);

        return googlePlacesData1;

    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parse(s);
        showNearbyPlace(nearbyPlaceList);

    }

    private void showNearbyPlace(List<HashMap<String, String>> nearbyPlaceList){

        /*MapsActivity ma = new MapsActivity();
        float distance[] = ma.getDistance();*/

        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble( googlePlace.get("lat"));
            double lng = Double.parseDouble( googlePlace.get("lng"));

            LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : "+ vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            mMap.addMarker(markerOptions);

        }
    }
}
