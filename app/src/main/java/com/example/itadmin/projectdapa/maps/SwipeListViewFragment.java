package com.example.itadmin.projectdapa.maps;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.itadmin.projectdapa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class SwipeListViewFragment extends Fragment {

    private Bundle bundle = new Bundle();
    private String jsonData;
    private ArrayList<Places> placesList;
    private Places places;
    private SwipeMenuListView swipeMenuListView;
    private PlacesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maps_swipe_listview, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        swipeMenuListView = getView().findViewById(R.id.listView);
        bundle = this.getArguments();
        jsonData = bundle.getString("jsonData");

        parseResult(jsonData);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem detailedView = new SwipeMenuItem(getActivity());
                detailedView.setBackground(new ColorDrawable(rgb(0, 0, 255)));
                detailedView.setWidth(170);
                detailedView.setIcon(R.drawable.swipe_info);
                menu.addMenuItem(detailedView);

                SwipeMenuItem call = new SwipeMenuItem(getActivity());
                call.setBackground(new ColorDrawable(rgb(51, 204, 51)));
                call.setWidth(170);
                call.setIcon(R.drawable.swipe_call);
                menu.addMenuItem(call);
            }
        };

        swipeMenuListView.setMenuCreator(creator);

        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(getActivity(), "you clicked detailed view", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "you clicked call", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });

    }

    private void parseResult(String result){
        try {
            JSONObject weatherJSON = new JSONObject(result);
            placesList = new ArrayList<Places>();

            JSONArray listArray = weatherJSON.getJSONArray("results");
            String name = "";
            String vicinity = "";
            for(int ctr=0; ctr < listArray.length(); ctr++) {
                JSONObject p = (JSONObject) listArray.get(ctr);
                name = p.getString("name");
                vicinity = p.getString("vicinity");

                places = new Places(name, vicinity);
                placesList.add(places);
            }
        }catch(JSONException jsone){
            jsone.printStackTrace();
        }

        adapter = new PlacesAdapter(getActivity(), R.layout.cardview, placesList);

        //ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, placesList);
        swipeMenuListView.setAdapter((ListAdapter) adapter);

    }

}
