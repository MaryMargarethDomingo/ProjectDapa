package com.example.itadmin.projectdapa.maps;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getActivity(),"list view start", Toast.LENGTH_LONG);

        recyclerView = getView().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new GetData().execute();

    }

    public class GetData extends AsyncTask<String, Void, Integer> {

        private List<Places> placesList;
        private PlacesAdapter placesAdapter;

        @Override
        protected Integer doInBackground(String... strings) {

            HttpURLConnection httpURLConnection;
            int result = 0;

            try {
                URL url = new URL(MapsFragment.getUrl1());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                int statusCode = httpURLConnection.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                Log.d("PARSER", MapsFragment.getUrl1());
                Log.d("PARSER", sb.toString());
                ParseResults(sb.toString());

                if (statusCode == 200) {
                    result = 1;
                } else {
                    result = 0;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                placesAdapter = new PlacesAdapter(getActivity(), placesList);
                recyclerView.setAdapter(placesAdapter);

            } else {
                Toast.makeText(getActivity(), "ERROR ON POST EXEUTE", Toast.LENGTH_LONG).show();
            }

        }

        private void ParseResults(String result) {
            try {

                JSONObject json = new JSONObject(result);
                placesList = new ArrayList<>();

                Places places = new Places(json.getString("name"), json.getString("vicinity"));

                placesList.add(places);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
