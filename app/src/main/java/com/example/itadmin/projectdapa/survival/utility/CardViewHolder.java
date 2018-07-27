package com.example.itadmin.projectdapa.survival.utility;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.itadmin.projectdapa.R;

/**
 * Created by Mary Domingo on 05/03/2018.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    ImageView ivPic;

    public CardViewHolder(View itemView) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
    }
}
