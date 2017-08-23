package com.tahutelorcommunity.bakingapp.viewholder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tahutelorcommunity.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Fikry-PC on 8/19/2017.
 */

public class RecipeViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.recipe_name) public TextView recipe_name;
    @BindView(R.id.recipe_servings) public TextView recipe_servings;
    @BindView(R.id.recipe_image) public ImageView recipe_image;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
