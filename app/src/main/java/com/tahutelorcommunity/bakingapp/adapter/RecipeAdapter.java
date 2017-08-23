package com.tahutelorcommunity.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.ui.StepsActivity;
import com.tahutelorcommunity.bakingapp.viewholder.RecipeViewHolder;

import java.util.ArrayList;

/**
 * Created by Fikry-PC on 8/19/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{
    private ArrayList<Recipe> recipes;
    private Context context;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachParentImmediately = false;
        View view = inflater.inflate(R.layout.list_recipe, parent, shouldAttachParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        final Bundle bundle = new Bundle();

        String servings = String.format(
                context.getString(R.string.recipe_servings,
                        recipe.getServings())
        );
        bundle.putParcelable(StepsActivity.RECIPE_BUNDLE_KEY, recipe);
        holder.recipe_name.setText(recipe.getName());
        holder.recipe_servings.setText(servings);
        if(recipe.getImage().length() != 0){
            Picasso.with(context).load(recipe.getImage()).placeholder(context.getDrawable(R.drawable.cook))
                    .into(holder.recipe_image);
        } else{
            holder.recipe_image.setImageResource(R.drawable.cook);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StepsActivity.class);
                intent.putExtra(StepsActivity.RECIPE_EXTRA_KEY, bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
