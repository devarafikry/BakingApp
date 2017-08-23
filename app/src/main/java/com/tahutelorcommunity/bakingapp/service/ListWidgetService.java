package com.tahutelorcommunity.bakingapp.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.tahutelorcommunity.bakingapp.factory.ListRemoteViewFactory;
import com.tahutelorcommunity.bakingapp.model.Ingredient;
import com.tahutelorcommunity.bakingapp.model.Recipe;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by Fikry-PC on 8/20/2017.
 */

public class ListWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext());
    }
}
