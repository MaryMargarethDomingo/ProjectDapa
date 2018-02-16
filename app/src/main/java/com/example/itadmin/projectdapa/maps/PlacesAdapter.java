package com.example.itadmin.projectdapa.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;

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
/*

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;

import java.util.List;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    List<Places> placesList;

    PlacesAdapter(List<Places> list){
        this.placesList = list;
    }

    @Override
    public PlacesAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        PlaceViewHolder pvh = new PlaceViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(PlacesAdapter.PlaceViewHolder holder, int position) {
        holder.tvName.setText(placesList.get(position).getName());
        holder.tvVicinity.setText(placesList.get(position).getVicinity());
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tvName;
        TextView tvVicinity;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            tvName = itemView.findViewById(R.id.txtPlace);
            tvVicinity = itemView.findViewById(R.id.txtVicinity);
        }
    }
}
*/
