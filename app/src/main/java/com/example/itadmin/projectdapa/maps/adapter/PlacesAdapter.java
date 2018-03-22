package com.example.itadmin.projectdapa.maps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.maps.model.Places;

import java.util.List;

public class PlacesAdapter extends ArrayAdapter<Places> {

    public PlacesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PlacesAdapter(Context context, int resource, List<Places> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.cardview, null);
        }

        Places p = getItem(position);

        if (p != null) {
            TextView tt1 = v.findViewById(R.id.txtPlace);
            TextView tt2 = v.findViewById(R.id.txtVicinity);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(p.getVicinity());
            }
        }

        return v;
    }

}
