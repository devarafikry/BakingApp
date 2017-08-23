package com.tahutelorcommunity.bakingapp.provider;


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Fikry-PC on 8/17/2017.
 */

public class RecipeContract {
    public static final String AUTHORITY = "com.tahutelorcommunity.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_RECIPE = "Recipe";

    public static final class RecipeEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_RECIPE = "Recipe";
        public static final String COLUMN_RECIPE_JSON = "recipeJson";
    }
}
