package com.example.itadmin.projectdapa.maps;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;

/**
 * Created by HI FREQUENCY on 2/4/2018.
 */

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration;
    String distance;

    @Override
    protected String doInBackground(Object... objects) {

        String url;

        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadURL downloadURL = new DownloadURL();

        try {
            googleDirectionsData = downloadURL.readURL(url);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("GOOGLE DIRECTIONS DATA", googleDirectionsData);

        return googleDirectionsData;
    }

//--------------------------------------------- (Duration, distance) ---------------------------------------------

    /*@Override
    protected void onPostExecute(String s) {
        HashMap<String, String> directionsList = null;
        DataParser dataParser = new DataParser();
        directionsList = dataParser.parseDirections(s);

        duration = directionsList.get("duration");
        distance = directionsList.get("distance");

    }*/

//--------------------------------------------- ********** ---------------------------------------------

    @Override
    protected void onPostExecute(String s) {
        String[] directionsList;
        DataParser dataParser = new DataParser();
        directionsList = dataParser.parseDirections(s);
        displayDirections(directionsList);

    }

    public void displayDirections(String[] directionsList){

        int count = directionsList.length;
        Polyline polylineFinal = null;
        PolylineOptions options;

        for(int i = 0; i < count; i++){

            options = new PolylineOptions();
            options.color(Color.RED);
            options.width(5);
            options.geodesic(true);
            options.addAll(PolyUtil.decode(directionsList[i]));

            polylineFinal = mMap.addPolyline(options);
        }

        polylineFinal.remove();
    }
}
