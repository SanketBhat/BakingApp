package com.udacity.sanketbhat.bakingapp.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.udacity.sanketbhat.bakingapp.Dependencies;
import com.udacity.sanketbhat.bakingapp.api.RecipeService;
import com.udacity.sanketbhat.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    private RecipeService recipeService;
    private MutableLiveData<List<Recipe>> recipeLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recipeService = Dependencies.getRecipeService();
        recipeLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        if (recipeLiveData.getValue() == null) {
            recipeService.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (response.isSuccessful()) {
                        recipeLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(getApplication().getApplicationContext(),
                            "Failed to get recipe list!",
                            Toast.LENGTH_LONG)
                            .show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return recipeLiveData;
    }

}
