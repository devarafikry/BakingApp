package com.tahutelorcommunity.bakingapp.utils;

import android.content.Context;

import com.tahutelorcommunity.bakingapp.R;
import com.tahutelorcommunity.bakingapp.model.Ingredient;
import com.tahutelorcommunity.bakingapp.model.Recipe;
import com.tahutelorcommunity.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fikry-PC on 8/19/2017.
 */

public class JsonUtils {
    public static ArrayList<Recipe> parseRecipeJson(String json_string, Context context){
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray data = new JSONArray(json_string);
            for (int i =0;i<data.length();i++){
                JSONObject recipe_json = data.getJSONObject(i);

                String id = recipe_json.getString(context.getString(R.string.node_recipe_id));
                String name = recipe_json.getString(context.getString(R.string.node_recipe_name));
                String servings = recipe_json.getString(context.getString(R.string.node_recipe_servings));
                String image = recipe_json.getString(context.getString(R.string.node_recipe_image));

                String ingredients_json = recipe_json.getString(context.getString(R.string.node_recipe_ingredients));
                String steps_json = recipe_json.getString(context.getString(R.string.node_recipe_steps));

                Recipe recipe = new Recipe(
                        id,
                        name,
                        parseIngredients(ingredients_json, context),
                        parseSteps(steps_json, context),
                        servings,
                        image
                );

                recipes.add(recipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public static ArrayList<Ingredient> parseIngredients(String json_string, Context context){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(json_string);

            for (int i =0;i<data.length();i++){
                JSONObject ingredient_json = data.getJSONObject(i);

                String quantity = ingredient_json.getString(context.getString(R.string.node_ingredients_quantity));
                String measure = ingredient_json.getString(context.getString(R.string.node_ingredients_measure));
                String ingredient = ingredient_json.getString(context.getString(R.string.node_ingredients_ingredient));

                Ingredient ingredient_obj = new Ingredient(
                        quantity,
                        measure,
                        ingredient
                );

                ingredients.add(ingredient_obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  ingredients;
    }

    public static ArrayList<Step> parseSteps(String json_string, Context context){
        ArrayList<Step> steps = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(json_string);

            for (int i =0;i<data.length();i++){
                JSONObject step_json = data.getJSONObject(i);

                String id = step_json.getString(context.getString(R.string.node_steps_id));
                String shordDescription = step_json.getString(context.getString(R.string.node_steps_shortDescription));
                String description = step_json.getString(context.getString(R.string.node_steps_description));
                String videoURL = step_json.getString(context.getString(R.string.node_steps_videoURL));
                String thumbnailURL = step_json.getString(context.getString(R.string.node_steps_thumbnailURL));

                Step step = new Step(
                        id,
                        shordDescription,
                        description,
                        videoURL,
                        thumbnailURL
                );

                steps.add(step);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  steps;
    }
}
