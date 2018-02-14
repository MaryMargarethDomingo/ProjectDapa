package com.example.itadmin.projectdapa.maps;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.itadmin.projectdapa.R;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class SwipeListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_listview);

        final SwipeMenuListView swipeMenuListView = findViewById(R.id.swipeMenuLayout);

        ArrayList<String> placesList = new ArrayList<>();
        placesList.add("DAPA");
        placesList.add("DAPA");
        placesList.add("DAPA");
        placesList.add("DAPA");
        placesList.add("DAPA");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        swipeMenuListView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem detailedView = new SwipeMenuItem(getApplicationContext());
                detailedView.setBackground(new ColorDrawable(rgb(0, 0, 255)));
                detailedView.setWidth(170);
                detailedView.setIcon(R.drawable.swipe_info);
                menu.addMenuItem(detailedView);

                SwipeMenuItem call = new SwipeMenuItem(getApplicationContext());
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
                        Toast.makeText(getApplicationContext(), "you clicked call", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "you clicked detailed view", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
    }
}
