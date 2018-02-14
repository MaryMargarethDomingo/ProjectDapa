package com.example.itadmin.projectdapa.maps;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.itadmin.projectdapa.R;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class SwipeListViewFragment extends Fragment {

    public SwipeListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        SwipeMenuListView swipeMenuListView = view.findViewById(R.id.swipeMenuLayout);

        ArrayList<String> placesList = new ArrayList<>();
        placesList.add("DAPA");
        placesList.add("DAPA");
        placesList.add("DAPA");
        placesList.add("DAPA");
        placesList.add("DAPA");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
        swipeMenuListView.setAdapter(adapter);

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
                        Toast.makeText(getActivity(), "you clicked call", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "you clicked detailed view", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });

        return view;
    }
/*

    @Override
    public void onStart(){
        super.onStart();



    }


*/

}
