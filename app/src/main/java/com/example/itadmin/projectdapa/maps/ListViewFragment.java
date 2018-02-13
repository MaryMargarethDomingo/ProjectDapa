package com.example.itadmin.projectdapa.maps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.itadmin.projectdapa.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment {

    RecyclerView recyclerView;
    private Bundle bundle = new Bundle();
    private String jsonData;
    private List<Places> list;
    private PlacesAdapter adapter;
    private Places places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getActivity(),"places view start", Toast.LENGTH_LONG);

        bundle = this.getArguments();
        jsonData = bundle.getString("jsonData");

        recyclerView = getView().findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        parseResult(jsonData);
    }

    private void parseResult(String result){
        try {
            JSONObject weatherJSON = new JSONObject(result);
            list = new ArrayList<>();

            JSONArray listArray = weatherJSON.getJSONArray("results");
            String name = "";
            String vicinity = "";
            for(int ctr=0; ctr < listArray.length(); ctr++) {
                JSONObject p = (JSONObject) listArray.get(ctr);
                name = p.getString("name");
                vicinity = p.getString("vicinity");

                places = new Places(name, vicinity);
                list.add(places);
            }
        }catch(JSONException jsone){
            jsone.printStackTrace();
        }

        adapter = new PlacesAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    /*@Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }*/
}

