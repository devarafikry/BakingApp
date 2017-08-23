package com.tahutelorcommunity.bakingapp.factory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.model.Ingredient;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.ui.StepDetailActivity;
import com.tahutelorcommunity.bakingapp.ui.StepsActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Fikry-PC on 8/20/2017.
 */

public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    Recipe recipe;
    List<Ingredient> ingredients;

    public ListRemoteViewFactory(Context context){
        this.mContext = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.recipe_shared_preference), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(sharedPreferences.getString(mContext.getString(R.string.recipe_key),""),
                Recipe.class);
        if(recipe != null){
            this.recipe = recipe;
            this.ingredients = recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredients == null) return 0;
        else return ingredients.size()-1;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(),
                R.layout.list_ingredients_widget
        );

        if(ingredients != null){
            Ingredient ingredient = ingredients.get(i);
            remoteViews.setTextViewText(R.id.ingredient, ingredient.getIngredient());
            String quantity = String.format(mContext.getString(R.string.ingredient_quantity),
                    String.valueOf(ingredient.getQuantity()),
                    ingredient.getMeasure());

            remoteViews.setTextViewText(R.id.quantity, quantity);
            Intent fillIntent = new Intent();
            fillIntent.putExtra(StepsActivity.RECIPE_EXTRA_KEY, recipe);
            remoteViews.setOnClickFillInIntent(R.id.list_linear_layout, fillIntent);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
