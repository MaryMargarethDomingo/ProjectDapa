package com.example.itadmin.projectdapa.weather.controller;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.MainActivity;
import com.example.itadmin.projectdapa.R;

public class RecyclerViewFragment extends Fragment {

    public RecyclerViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_weather_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weatherRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final SwipeRefreshLayout swipeLayout = view.findViewById(R.id.swipe_layout1);

        MainActivity mainActivity = (MainActivity) getActivity();
        recyclerView.setAdapter(mainActivity.weatherFragment.getAdapter(bundle.getInt("day")));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean enableRefresh = false;
            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {}
            @Override
            public void onScrolled(RecyclerView view, int dx, int dy) {
                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    // check that the first item is visible and that its top matches the parent
                    enableRefresh = recyclerView.getChildAt(0).getTop() >= 0;
                } else {
                    enableRefresh = false;
                }
                swipeLayout.setEnabled(enableRefresh);
            }
        });

        // specify action to take on swipe refresh
        swipeLayout.setOnRefreshListener(() -> {
            swipeLayout.setRefreshing(true);
            if (MainActivity.weatherFragment.isNetworkAvailable()) {
                MainActivity.weatherFragment.getTodayWeather();
                MainActivity.weatherFragment.getLongTermWeather();
            } else {
                Snackbar.make(MainActivity.weatherFragment.appView, getString(R.string.msg_connection_not_available), Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
