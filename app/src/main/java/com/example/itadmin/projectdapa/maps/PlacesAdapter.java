package com.example.itadmin.projectdapa.maps;

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
