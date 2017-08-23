package com.tahutelorcommunity.bakingapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Fikry-PC on 8/20/2017.
 */

public class IngredientService extends IntentService {
    public static final String ACTION_SHOW_INGREDIENT = "showIngredient";
    public static final String ACTION_UPDATE_INGREDIENT = "updateIngredient";
    public IngredientService() {
        super("IngredientService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
