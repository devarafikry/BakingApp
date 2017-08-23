package com.tahutelorcommunity.bakingapp.ui;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.adapter.StepAdapter;
import com.tahutelorcommunity.bakingapp.model.Ingredient;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.model.Step;
import com.tahutelorcommunity.bakingapp.utils.SnackbarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterListFragment extends Fragment{
    public static final String RECIPE_EXTRA_KEY = "recipe";
    public static final String RECIPE_BUNDLE_KEY = "recipeBundle";
    public static final String STEP_POSITION_KEY = "positionKey";
    public static final String POSITION_BUNDLE_KEY = "position";

    Recipe recipe = null;
    boolean todo = false;
    @BindView(R.id.step_recycler_view)
    RecyclerView step_recycler_view;
    @BindView(R.id.ingredients)
    TextView text_view_ingredients;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.todo_button)
    ImageView todo_button;
    @BindView(R.id.master_list_coordinator_layout)
    CoordinatorLayout master_list_coordinator_layout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    int position = 0;
    int lastPosition = 0;
    private final String LAST_POSITION_KEY = "lastPosition";

    Snackbar snackbar;
    LinearLayoutManager linearLayoutManager;
    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getContext().getResources().getBoolean(R.bool.isTablet)){
            outState.putInt(LAST_POSITION_KEY, position);
        } else{
            outState.putInt(LAST_POSITION_KEY, linearLayoutManager.findFirstVisibleItemPosition());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_master_list, container, false);
        ButterKnife.bind(this, view);

        if(getArguments()!= null){
            position = getArguments().getInt(POSITION_BUNDLE_KEY);
            if(getContext().getResources().getBoolean(R.bool.isTablet)){
                lastPosition = position;
            }
            if(savedInstanceState != null){
                if(getContext().getResources().getBoolean(R.bool.isTablet)){
                    lastPosition = position;
                } else{
                    lastPosition = savedInstanceState.getInt(LAST_POSITION_KEY);
                }
            }



            if(getArguments().getParcelable(RECIPE_BUNDLE_KEY) != null){
                this.recipe = getArguments().getParcelable(RECIPE_BUNDLE_KEY);
                Timber.d("Recipe received : "+ recipe.getName());

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.recipe_shared_preference),Context.MODE_PRIVATE);
                boolean defaultValue = getActivity().getResources().getBoolean(R.bool.todo_default);
                todo = sharedPreferences.getBoolean(recipe.getId(), defaultValue);

                Log.d("SharedPreference", sharedPreferences.getString(getString(R.string.recipe_key),"gak ada i"));
                if(todo){
                    todo_button.setImageResource(R.drawable.todo);
                } else{
                    todo_button.setImageResource(R.drawable.todo_white);
                }
                String title = String.format(
                        getString(R.string.recipe),
                        recipe.getName()
                );
                toolbar.setTitle(title);
                toolbar.setNavigationIcon(R.drawable.ic_back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().onBackPressed();
                    }
                });
                StepAdapter adapter = new StepAdapter(position, recipe, recipe.getSteps(), view.getContext(), (StepsActivity)getActivity());
                linearLayoutManager = new LinearLayoutManager(view.getContext());

                step_recycler_view.setLayoutManager(linearLayoutManager);
                step_recycler_view.setAdapter(adapter);


                if(lastPosition != 0){
                    appBarLayout.setExpanded(false);
                } else {
                    appBarLayout.setExpanded(true);
                }
                step_recycler_view.scrollToPosition(lastPosition);


                ArrayList<Ingredient> ingredients = recipe.getIngredients();
                for (int i=0;i<ingredients.size();i++){
                    Ingredient ingredient = ingredients.get(i);
                    String ingredient_text = ingredient.getIngredient()+" "+
                            String.format(getString(R.string.ingredient_quantity),
                                    ingredient.getQuantity(),
                                    ingredient.getMeasure());
                    String current_ingredient = text_view_ingredients.getText().toString();
                    text_view_ingredients.setText(current_ingredient + "\n"+ingredient_text);
                }

            }

        }

        todo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todoRecipe();
            }
        });
        return view;
    }

    private void todoRecipe(){
        String s ;
        if(todo){
            todo_button.setImageResource(R.drawable.todo_white);
            todo = false;
            s = "You removed this recipe from todo list.";
        } else{
            todo_button.setImageResource(R.drawable.todo);
            todo = true;
            s = "You added this recipe to todo list.";
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.recipe_shared_preference),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
        editor.putBoolean(recipe.getId(), todo);

        Gson gson = new Gson();
        editor.putString(getString(R.string.recipe_key), gson.toJson(recipe));
        editor.commit();
        updateWidget();
        SnackbarUtil.showSnackBar(master_list_coordinator_layout, snackbar, s, Snackbar.LENGTH_LONG);
    }

    private void updateWidget(){
        AppWidgetManager man = AppWidgetManager.getInstance(getContext());
        int[] ids = man.getAppWidgetIds(
                new ComponentName(getContext(),IngredientsWidgetProvider.class));
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra(IngredientsWidgetProvider.WIDGET_IDS_KEY, ids);
        getContext().sendBroadcast(updateIntent);
    }

}
