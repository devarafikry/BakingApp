package com.tahutelorcommunity.bakingapp.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tahutelorcommunity.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fikry-PC on 8/19/2017.
 */

public class StepViewHolder extends RecyclerView.ViewHolder {

    @Nullable @BindView(R.id.step_number) public TextView step_number;
    @Nullable @BindView(R.id.step_short_description) public TextView step_short_description;
    @Nullable @BindView(R.id.card_step) public CardView card_step;
    @Nullable @BindView(R.id.step_thumbnail) public ImageView step_thumbnail;
    public StepViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
