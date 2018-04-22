package com.example.itadmin.projectdapa.survival.utility;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itadmin.projectdapa.R;
import com.example.itadmin.projectdapa.survival.controller.SurvivalCheckBoxFragment;
import com.example.itadmin.projectdapa.survival.controller.SurvivalPagerFragment;
import com.example.itadmin.projectdapa.survival.model.SurvivalBean;

import java.util.List;

/**
 * Created by Mary Domingo on 05/03/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private List<SurvivalBean> cardDataList;
    private Context context;
    private FragmentManager fragmentManager;

    public CardAdapter (Context context, List<SurvivalBean> cardDataList, FragmentManager fragmentManager) {
        this.cardDataList = cardDataList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.survival_adapter, parent, false);

        return new CardViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final SurvivalBean cardData = cardDataList.get(position);
        holder.tvTitle.setText(cardData.mTitle);
        holder.ivPic.setImageResource(cardData.mPicId);
        holder.ivPic.setOnClickListener(view -> inflate(holder.getAdapterPosition()));

    }

    public void inflate(int position){
        //Toast.makeText(MainActivity.contextOfApplication, "Index: " + position, Toast.LENGTH_LONG).show();

        if(position == 0){
            SurvivalCheckBoxFragment survivalCheckBoxFragment = new SurvivalCheckBoxFragment();
            fragmentManager.beginTransaction().replace(R.id.include2, survivalCheckBoxFragment).addToBackStack(null).commit();

        }else{
            SurvivalPagerFragment survivalPagerFragment = new SurvivalPagerFragment(position);
            fragmentManager.beginTransaction().replace(R.id.include2, survivalPagerFragment).addToBackStack(null).commit();
        }

    }

    @Override
    public int getItemCount() {
        return cardDataList.size();
    }


}
