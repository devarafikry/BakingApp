package com.tahutelorcommunity.bakingapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fikry-PC on 8/17/2017.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipeDb.db";
    private static final int VERSION = 1;

    RecipeDbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    private String CREATE_RECIPE_TABLE() {
        return "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_RECIPE + " ("
                + RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY, "
                + RecipeContract.RecipeEntry.COLUMN_RECIPE_JSON + " TEXT NOT NULL);";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECIPE_TABLE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
