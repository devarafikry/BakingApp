package com.tahutelorcommunity.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.picasso.Picasso;
import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.model.Ingredient;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.model.Step;
import com.tahutelorcommunity.bakingapp.ui.StepDetailActivity;
import com.tahutelorcommunity.bakingapp.viewholder.StepViewHolder;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Fikry-PC on 8/19/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder>{
    private List<Step> steps;
    private Context mContext;
    private Recipe recipe;
    private int position;
    private OnStepClickListener onStepClickListener;
    private static final int SELECTED_ITEM_KEY = 88;
    private static final int TYPE_NEXT = 1;
    private static final int TYPE_PREV = 0;
    StepViewHolder selected_view_holder = null;

    private int INTRO_VIEW_TYPE = 0;
    private int STEP_VIEW_TYPE = 1;

    public interface OnStepClickListener {
        void onStepClick(Step step, String description, int position);
    }

    public StepAdapter(int position, Recipe recipe, List<Step> steps, Context context, OnStepClickListener onStepClickListener) {
        this.recipe = recipe;
        this.steps = steps;
        this.mContext = context;
        this.onStepClickListener = onStepClickListener;
        this.position = position;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if(viewType == INTRO_VIEW_TYPE){
            layout = R.layout.list_introduction;
        } else{
            layout = R.layout.list_step;
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachParentImmediately = false;
        View view = inflater.inflate(layout, parent, shouldAttachParentImmediately);
        return new StepViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return INTRO_VIEW_TYPE;
        else return STEP_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(final StepViewHolder holder, final int position) {
        final Step step = steps.get(holder.getAdapterPosition());
        final String description;
        int type = getItemViewType(position);
        int adapterPosition = holder.getAdapterPosition();
        if(adapterPosition == this.position){
            holder.card_step.setCardBackgroundColor(mContext.getResources().getColor(
                    R.color.colorAccent
            ));
            selected_view_holder = holder;
        } else{
            holder.card_step.setCardBackgroundColor(mContext.getResources().getColor(
                    android.R.color.white
            ));
        }
        if(type == INTRO_VIEW_TYPE){
            description = step.getDescription();
            CardView.LayoutParams layoutParams = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,(int)mContext.getResources().getDimension(R.dimen.card_recipe_margin));
//            holder.step_thumbnail.setVisibility(View.GONE);
            holder.card_step.setLayoutParams(layoutParams);
//            holder.card_step.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
            holder.step_short_description.setText(step.getShortDescription());
            if(!TextUtils.isEmpty(step.getThumbnailUrl())){
                Picasso.with(mContext).load(step.getThumbnailUrl()).placeholder(R.drawable.step)
                        .into(holder.step_thumbnail);
            } else{
                holder.step_thumbnail.setImageResource(R.drawable.introduction);
            }
        } else{
            description = step.getDescription().substring(3);
            holder.step_number.setText(String.valueOf(position));
            holder.step_short_description.setText(step.getShortDescription());
            if(!TextUtils.isEmpty(step.getThumbnailUrl())){
                Picasso.with(mContext).load(step.getThumbnailUrl()).placeholder(R.drawable.step)
                        .into(holder.step_thumbnail);
            } else{
                holder.step_thumbnail.setImageResource(R.drawable.step);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StepAdapter.this.position = holder.getAdapterPosition();
                if(selected_view_holder != null){
                    selected_view_holder.card_step.setCardBackgroundColor(mContext.getResources().getColor(android.R.color.white));
                }
                selected_view_holder = holder;
                holder.card_step.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
                Timber.d(step.getShortDescription());
                onStepClickListener.onStepClick(step, description, StepAdapter.this.position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(steps == null){
            return 0;
        }
        return steps.size();
    }

}
