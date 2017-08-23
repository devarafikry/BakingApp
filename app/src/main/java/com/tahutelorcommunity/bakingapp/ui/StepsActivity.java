package com.tahutelorcommunity.bakingapp.ui;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.adapter.StepAdapter;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StepsActivity extends AppCompatActivity implements StepAdapter.OnStepClickListener{

    public static final String RECIPE_EXTRA_KEY = "recipe";
    public static final String RECIPE_BUNDLE_KEY = "recipeBundle";
    public static final String POSITION_BUNDLE_KEY = "position";
    public static final String STEP_BUNDLE_KEY = "step";

    Recipe recipe;
    Boolean twoPane;

    public static boolean isTablet;
    int position =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);


        recipe = null;
        if(findViewById(R.id.step_detail_linear_layout) != null){
            twoPane = true;
            isTablet = true;
        } else{
            twoPane = false;
            isTablet = false;
        }

        Bundle bundle = getIntent().getBundleExtra(RECIPE_EXTRA_KEY);
        if(bundle.getParcelable(RECIPE_BUNDLE_KEY) != null){
            recipe = bundle.getParcelable(RECIPE_BUNDLE_KEY);
            Timber.d("Recipe received : "+ recipe.getName());
        }
        if(twoPane){
            if(savedInstanceState != null){
                int stepPosition = savedInstanceState.getInt(POSITION_BUNDLE_KEY);
                this.position = stepPosition;

                bundle.putInt(POSITION_BUNDLE_KEY, position);
                MasterListFragment masterListFragment = new MasterListFragment();
                masterListFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.master_list_frame_layout,
                        masterListFragment
                ).commit();
            }
            if(getIntent().hasExtra(RECIPE_EXTRA_KEY)){
                if(savedInstanceState == null){

//                    Bundle bundle = getIntent().getBundleExtra(RECIPE_EXTRA_KEY);
                    bundle.putInt(POSITION_BUNDLE_KEY, position);
                    MasterListFragment masterListFragment = new MasterListFragment();
                    masterListFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.master_list_frame_layout,
                            masterListFragment
                    ).commit();

                    ArrayList<Step> steps = recipe.getSteps();
                    Step step = steps.get(position);


                    ExoPlayerFragment exo_player_fragment = new ExoPlayerFragment();
                    Bundle exoPlayerBundle = new Bundle();
                    exoPlayerBundle.putString(ExoPlayerFragment.MEDIA_URI_KEY, step.getVideoUrl());
                    exo_player_fragment.setArguments(exoPlayerBundle);
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.container_exo_player,
                            exo_player_fragment
                    ).commit();

                    String description = step.getDescription();
                    DescriptionFragment description_fragment = new DescriptionFragment();
                    Bundle descriptionBundle = new Bundle();
                    descriptionBundle.putString(DescriptionFragment.DESCRIPTION_ARGS_KEY, description);
                    description_fragment.setArguments(descriptionBundle);
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.container_step_description,
                            description_fragment
                    ).commit();
                }
            }
        } else{
            if(getIntent().hasExtra(RECIPE_EXTRA_KEY)){
                if(savedInstanceState == null){
//                    Bundle bundle = getIntent().getBundleExtra(RECIPE_EXTRA_KEY);
                    MasterListFragment masterListFragment = new MasterListFragment();
                    masterListFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.master_list_frame_layout,
                            masterListFragment
                    ).commit();
                    if(bundle.getParcelable(RECIPE_BUNDLE_KEY) != null){
                        recipe = bundle.getParcelable(RECIPE_BUNDLE_KEY);
                        Timber.d("Recipe received : "+ recipe.getName());
                    }
                }
            }
        }
    }

    @Override
    public void onStepClick(Step step, String description, int position) {
        if(twoPane){
            this.position = position;
            ExoPlayerFragment exo_player_fragment = new ExoPlayerFragment();
            Bundle exoPlayerBundle = new Bundle();
            exoPlayerBundle.putString(ExoPlayerFragment.MEDIA_URI_KEY, step.getVideoUrl());
            exo_player_fragment.setArguments(exoPlayerBundle);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.container_exo_player,
                    exo_player_fragment
            ).commit();

            DescriptionFragment description_fragment = new DescriptionFragment();
            Bundle descriptionBundle = new Bundle();
            descriptionBundle.putString(DescriptionFragment.DESCRIPTION_ARGS_KEY, description);
            description_fragment.setArguments(descriptionBundle);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.container_step_description,
                    description_fragment
            ).commit();
        } else{
            Timber.d("position : "+ position);
            Timber.d("short : "+ step.getShortDescription());
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.STEP_POSITION_KEY, position);
            intent.putExtra(StepDetailActivity.RECIPE_KEY, recipe);
//          intent.putExtra(StepDetailActivity.SHORT_DESCRIPTION_KEY, step.getShortDescription());
            intent.putExtra(StepDetailActivity.DESCRIPTION_KEY, description);
//          intent.putExtra(StepDetailActivity.MEDIA_URI_KEY, step.getVideoUrl());
            startActivity(intent);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

//        Step step = savedInstanceState.getParcelable(STEP_BUNDLE_KEY);
//        if(twoPane){
//            this.position = Integer.valueOf(step.getId());
//            ExoPlayerFragment exo_player_fragment = new ExoPlayerFragment();
//            Bundle exoPlayerBundle = new Bundle();
//            exoPlayerBundle.putString(ExoPlayerFragment.MEDIA_URI_KEY, step.getVideoUrl());
//            exo_player_fragment.setArguments(exoPlayerBundle);
//            getSupportFragmentManager().beginTransaction().replace(
//                    R.id.container_exo_player,
//                    exo_player_fragment
//            ).commit();
//
//            String description = step.getDescription();
//            if(stepPosition != 0){
//                description = step.getDescription().substring(3);
//            }
//            DescriptionFragment description_fragment = new DescriptionFragment();
//            Bundle descriptionBundle = new Bundle();
//            descriptionBundle.putString(DescriptionFragment.DESCRIPTION_ARGS_KEY, description);
//            description_fragment.setArguments(descriptionBundle);
//            getSupportFragmentManager().beginTransaction().replace(
//                    R.id.container_step_description,
//                    description_fragment
//            ).commit();
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_BUNDLE_KEY, this.position);
        Timber.d(position+" Position in saved instance state");
        outState.putParcelable(STEP_BUNDLE_KEY, this.recipe.getSteps().get(position));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getParent() == null){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
