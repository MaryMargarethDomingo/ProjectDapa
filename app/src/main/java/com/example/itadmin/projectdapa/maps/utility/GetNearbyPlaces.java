package com.example.itadmin.projectdapa.maps.utility;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.itadmin.projectdapa.MainActivity;
import com.example.itadmin.projectdapa.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;


public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    private String type;
    private String phoneNumber;
    Context context = MainActivity.getContextOfApplication();

    @Override
    protected String doInBackground(Object... objects) {

        String url1;

        mMap = (GoogleMap) objects[0];
        url1 = (String) objects[1];
        type = (String) objects[2];

        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readURL(url1);

            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString("jsonData", googlePlacesData).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("DATAAAA", googlePlacesData);
        return googlePlacesData;

    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parse(s);
        showNearbyPlace(nearbyPlaceList);
    }

    private void showNearbyPlace(List<HashMap<String, String>> nearbyPlaceList){
        for(int i = 0; i < nearbyPlaceList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            String placeid = googlePlace.get("place_id");
            double lat = Double.parseDouble( googlePlace.get("lat"));
            double lng = Double.parseDouble( googlePlace.get("lng"));

            /*try {
                JSONObject placeDetails = readJsonFromUrl("https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyAI8AWBOjy-m-t281QDrwov3r2KImzc__A&placeid=" + placeid);
                phoneNumber = placeDetails.get("formatted_phone_number").toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            LatLng latLng = new LatLng( lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : "+ vicinity);
            //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            if(type.equals("hospital")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospitalmarker));
            }else if(type.equals("police")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.policemarker));
            }else if(type.equals("fire_station")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.firemarker));
            }else if(type.equals("veterinary_care")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.vetmarker));
            }else if(type.equals("school")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.evacmarker));
            }


            mMap.addMarker(markerOptions);

        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

}
