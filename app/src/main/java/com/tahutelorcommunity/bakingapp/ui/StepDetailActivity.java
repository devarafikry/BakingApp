package com.tahutelorcommunity.bakingapp.ui;

import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class StepDetailActivity extends AppCompatActivity {

    String description = null;
    String shortDescription = null;
    String s_thumbnailUri = null;
    String s_uri = null;
    Step step = null;
    Recipe recipe = null;
    int stepPosition = 0;

    private static final int TYPE_PREV = 0;
    private static final int TYPE_NEXT = 1;
    @BindView(R.id.step_next)
    TextView step_next;

    @BindView(R.id.step_prev)
    TextView step_prev;

    public static final String STEP_POSITION_KEY = "stepPosition";
    public static final String RECIPE_KEY = "recipe";
//    public static final String MEDIA_URI_KEY = "mediaUri";
    public static final String DESCRIPTION_KEY = "description";
//    public static final String SHORT_DESCRIPTION_KEY = "shortDescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        ButterKnife.bind(this);

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE){
            getSupportActionBar().hide();
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } else{
            getSupportActionBar().show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent().hasExtra(RECIPE_KEY)){
            recipe = getIntent().getParcelableExtra(RECIPE_KEY);
            if(getIntent().hasExtra(STEP_POSITION_KEY)){
                stepPosition = getIntent().getIntExtra(STEP_POSITION_KEY,0);
                step = recipe.getSteps().get(stepPosition);
                s_uri = step.getVideoUrl();
                s_thumbnailUri = step.getThumbnailUrl();
                shortDescription = step.getShortDescription();
            }

        }

        if(getIntent().hasExtra(DESCRIPTION_KEY)){
            this.description = getIntent().getStringExtra(DESCRIPTION_KEY);
            getSupportActionBar().setTitle(shortDescription);
        }

        if(recipe != null){
            setNavigation();

            step_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateFragment(TYPE_NEXT);
                }
            });

            step_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateFragment(TYPE_PREV);
                }
            });
        }

        if(savedInstanceState == null){
            replaceDescriptionFragment(description);
            replaceExoPlayerFragment(s_uri);
        }
    }

    private void setNavigation(){
        if(stepPosition == 0){
            step_prev.setVisibility(View.INVISIBLE);
        } else{
            step_prev.setVisibility(View.VISIBLE);
        }

        if(stepPosition == getMaxStep(recipe)){
            step_next.setVisibility(View.INVISIBLE);
        } else{
            step_next.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceDescriptionFragment(String description){
        DescriptionFragment description_fragment = new DescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DescriptionFragment.DESCRIPTION_ARGS_KEY, description);
        description_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container_step_description,
                description_fragment
        ).commit();
    }

    private void replaceExoPlayerFragment(String s_uri){
        ExoPlayerFragment exo_player_fragment = new ExoPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ExoPlayerFragment.MEDIA_URI_KEY, s_uri);
        exo_player_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(
                R.id.container_exo_player,
                exo_player_fragment
        ).commit();
    }

    private int getMaxStep(Recipe recipe){
        Timber.d("Size dari recipe :"+recipe.getSteps().size());
        return recipe.getSteps().size()-1;
    }

    private void updateFragment(int type){
        //prev = 0
        //next = 1
        switch (type){
            case TYPE_PREV:
                stepPosition--;
                break;
            case TYPE_NEXT:
                stepPosition++;
                break;
        }
        step = recipe.getSteps().get(stepPosition);
        getSupportActionBar().setTitle(step.getShortDescription());
        replaceExoPlayerFragment(step.getVideoUrl());
        if(stepPosition == 0){
            description = step.getDescription();
        } else{
            description = step.getDescription().substring(3);
        }
        replaceDescriptionFragment(description);
        setNavigation();
    }
}
