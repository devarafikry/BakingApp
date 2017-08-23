package com.tahutelorcommunity.bakingapp.utils;

import com.tahutelorcommunity.bakingapp.idlingResource.SimpleIdlingResource;

import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Fikry-PC on 8/19/2017.
 */

public class NetworkUtils {
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static Request buildRequest(String url){
        return new Request.Builder().url(url).build();
    }

    public static void getRecipeData(SimpleIdlingResource idlingResource, Callback callback){
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        OkHttpClient client = new OkHttpClient();
        client.newCall(buildRequest(BASE_URL)).enqueue(callback);
    }
}
