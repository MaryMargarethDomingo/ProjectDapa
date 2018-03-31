package com.example.itadmin.projectdapa.survival.utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.survival.controller.SurvivalCheckBoxFragment;
import com.example.itadmin.projectdapa.survival.controller.survivalContentPagers.SurvivalContentPagerEarthquake;
import com.example.itadmin.projectdapa.survival.controller.survivalContentPagers.SurvivalContentPagerFlood;
import com.example.itadmin.projectdapa.survival.controller.survivalContentPagers.SurvivalContentPagerTsunami;
import com.example.itadmin.projectdapa.survival.controller.survivalContentPagers.SurvivalContentPagerTyphoon;
import com.example.itadmin.projectdapa.survival.controller.survivalContentPagers.SurvivalContentPagerVolcano;
import com.example.itadmin.projectdapa.survival.model.SurvivalBean;

import java.util.List;

/**
 * Created by Mary Domingo on 16/02/2018.
 */

public class SurvivalRecyclerViewAdapter extends RecyclerView.Adapter<SurvivalRecyclerViewAdapter.MyViewHolder> {
    private List<SurvivalBean> survivalDis;
    private Fragment survivalContentPager;
    private FragmentManager fragmentManager;


    public SurvivalRecyclerViewAdapter(List<SurvivalBean> survivalDis, FragmentManager fragmentManager){
        this.survivalDis = survivalDis;
        this.fragmentManager = fragmentManager;
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
        holder.image.setOnClickListener(view -> inflate(holder.getAdapterPosition()));
    }

    public void inflate(int position){
        //Toast.makeText(MainActivity.contextOfApplication, "Index: " + position, Toast.LENGTH_LONG).show();

        switch(position){
            case 0:
                SurvivalCheckBoxFragment survivalCheckBoxFragment = new SurvivalCheckBoxFragment();
                fragmentManager.beginTransaction().replace(R.id.content_id, survivalCheckBoxFragment).addToBackStack(null).commit();
                break;

            case 1:
                survivalContentPager = new SurvivalContentPagerEarthquake();
                fragmentManager.beginTransaction().replace(R.id.content_id, survivalContentPager).addToBackStack(null).commit();
                break;

            case 2:
                survivalContentPager = new SurvivalContentPagerTyphoon();
                fragmentManager.beginTransaction().replace(R.id.content_id, survivalContentPager).addToBackStack(null).commit();
                break;

            case 3:
                survivalContentPager = new SurvivalContentPagerFlood();
                fragmentManager.beginTransaction().replace(R.id.content_id, survivalContentPager).addToBackStack(null).commit();
                break;

            case 4:
                survivalContentPager = new SurvivalContentPagerTsunami();
                fragmentManager.beginTransaction().replace(R.id.content_id, survivalContentPager).addToBackStack(null).commit();
                break;

            case 5:
                survivalContentPager = new SurvivalContentPagerVolcano();
                fragmentManager.beginTransaction().replace(R.id.content_id, survivalContentPager).addToBackStack(null).commit();
                break;

        }

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

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.contextOfApplication, "Index: " , Toast.LENGTH_LONG).show();
                    youtubeFragment = new YoutubeFragment(index);
                    ((MainActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content_id, youtubeFragment).addToBackStack(null).commit();

                }
            });*/

        }
    }
}