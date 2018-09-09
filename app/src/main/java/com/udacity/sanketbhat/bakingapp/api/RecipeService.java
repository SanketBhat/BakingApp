package com.udacity.sanketbhat.bakingapp.api;

import com.udacity.sanketbhat.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {


    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
