package com.tahutelorcommunity.bakingapp.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.service.ListWidgetService;

import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String WIDGET_IDS_KEY ="mywidgetproviderwidgetids";
    public static final String WIDGET_DATA_KEY ="mywidgetproviderwidgetdata";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(WIDGET_IDS_KEY)) {
            int[] ids = intent.getExtras().getIntArray(WIDGET_IDS_KEY);
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
        } else super.onReceive(context, intent);
    }

    static void updateAppWidget(Recipe recipe, Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent onClickIntent;
        if(recipe != null){
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.recipe_shared_preference), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            Boolean show = sharedPreferences.getBoolean(recipe.getId(),false);
            views.setViewVisibility(R.id.error_text, GONE);
            if(!show){
                views.setTextViewText(R.id.recipe, "");
                views.setViewVisibility(R.id.error_text, VISIBLE);
                views.setViewVisibility(R.id.widget_list_view, GONE);
                onClickIntent = new Intent(context, MainActivity.class);
            } else{
                views.setTextViewText(R.id.recipe, recipe.getName());
                views.setViewVisibility(R.id.error_text, GONE);
                views.setViewVisibility(R.id.widget_list_view, VISIBLE);
                onClickIntent = new Intent(context, StepsActivity.class);
            }
        } else{
            views.setViewVisibility(R.id.error_text, VISIBLE);
            onClickIntent = new Intent(context, MainActivity.class);
        }
        Intent intent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        Bundle bundle = new Bundle();
        bundle.putParcelable(StepsActivity.RECIPE_BUNDLE_KEY, recipe);
        onClickIntent.putExtra(StepsActivity.RECIPE_EXTRA_KEY, bundle);
        PendingIntent onClickPendingIntent = PendingIntent.getActivity(
                context,
                0,
                onClickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        onClickIntent.putExtra(StepsActivity.RECIPE_EXTRA_KEY, recipe);


        views.setOnClickPendingIntent(R.id.widget_root_view, onClickPendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.recipe_shared_preference), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(sharedPreferences.getString(context.getString(R.string.recipe_key),""),
                    Recipe.class);
//            Timber.d(recipe.getName());
//            Log.d("sharedcoba",sharedPreferences.getString(context.getString(R.string.ingredient_key),""));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list_view);
            updateAppWidget(recipe, context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

