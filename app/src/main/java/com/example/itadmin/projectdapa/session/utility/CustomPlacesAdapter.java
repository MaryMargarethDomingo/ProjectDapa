package com.example.itadmin.projectdapa.session.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.itadmin.projectdapa.R;

public class CustomPlacesAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<String> places;
    LayoutInflater inflater;

    public CustomPlacesAdapter(Context mContext, ArrayList<String> places) {
      this.mContext = mContext;
      this.places = places;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(inflater==null)
        {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.custom_places_row,parent,false);
        }

        TextView tvPlace = convertView.findViewById(R.id.tvPlace);


        notifyDataSetChanged();
        return convertView;
    }

}
