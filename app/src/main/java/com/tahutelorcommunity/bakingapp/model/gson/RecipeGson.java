
package com.tahutelorcommunity.bakingapp.model.gson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeGson {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<com.tahutelorcommunity.bakingapp.model.Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<StepGson> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<com.tahutelorcommunity.bakingapp.model.Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<com.tahutelorcommunity.bakingapp.model.Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepGson> getSteps() {
        return steps;
    }

    public void setSteps(List<StepGson> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
