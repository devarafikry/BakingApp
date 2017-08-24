package com.tahutelorcommunity.bakingapp.ui;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.adapter.RecipeAdapter;
import com.tahutelorcommunity.bakingapp.idlingResource.SimpleIdlingResource;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.utils.JsonUtils;
import com.tahutelorcommunity.bakingapp.utils.NetworkUtils;
import com.tahutelorcommunity.bakingapp.utils.SnackbarUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import timber.log.Timber;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity implements Callback, LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{
    private final int LOADER_PARSE_JSON_ID = 78;
    private final String BUNDLE_JSON_DATA = "jsonData";
    private Snackbar snackbar;

    @Nullable
    private SimpleIdlingResource idlingResource;

    @BindView(R.id.recipe_recycler_view)
    RecyclerView recipe_recycler_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);
        idlingResource = getSimpleIdlingResource();
        NetworkUtils.getRecipeData(idlingResource, this);
    }

    @VisibleForTesting
    @Nullable
    public SimpleIdlingResource getSimpleIdlingResource(){
        if(idlingResource == null){
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        SnackbarUtil.showSnackBar(findViewById(R.id.main_activity_frame_layout),
                snackbar,
                getString(R.string.no_connection_main),
                Snackbar.LENGTH_LONG);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_JSON_DATA, response.body().string());
        getSupportLoaderManager().restartLoader(LOADER_PARSE_JSON_ID, bundle, this);
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        final String jsonData = args.getString(BUNDLE_JSON_DATA);
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {
                return JsonUtils.parseRecipeJson(jsonData, MainActivity.this);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        RecipeAdapter adapter = new RecipeAdapter(this, data);
        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
            recipe_recycler_view.setLayoutManager(gridLayoutManager);
        } else{
            if(getResources().getBoolean(R.bool.isTablet)){
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
                recipe_recycler_view.setLayoutManager(gridLayoutManager);
            } else{
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recipe_recycler_view.setLayoutManager(linearLayoutManager);
            }
        }

        recipe_recycler_view.setAdapter(adapter);
        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }
}
