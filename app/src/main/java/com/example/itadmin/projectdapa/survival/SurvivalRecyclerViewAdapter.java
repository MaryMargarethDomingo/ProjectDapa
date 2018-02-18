package com.example.itadmin.projectdapa.survival;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itadmin.projectdapa.MainActivity;
import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.survival.Youtube.YoutubeFragment;

import java.util.List;

/**
 * Created by Mary Domingo on 16/02/2018.
 */

public class SurvivalRecyclerViewAdapter extends RecyclerView.Adapter<SurvivalRecyclerViewAdapter.MyViewHolder> {
    private List<SurvivalBean> survivalDis;
    private YoutubeFragment youtubeFragment = new YoutubeFragment();


    SurvivalRecyclerViewAdapter(List<SurvivalBean> survivalDis){
        this.survivalDis = survivalDis;
    }

    @Override
    public SurvivalRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survival_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurvivalRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.list.setText(survivalDis.get(position).getList());
        holder.image.setBackgroundResource(survivalDis.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return survivalDis.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView list;
        private ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);
            list = itemView.findViewById(R.id.list);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content_id, youtubeFragment).addToBackStack(null).commit();
                }
            });

        }
    }
}
