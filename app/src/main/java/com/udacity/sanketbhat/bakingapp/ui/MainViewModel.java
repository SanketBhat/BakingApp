package com.udacity.sanketbhat.bakingapp.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.udacity.sanketbhat.bakingapp.Dependencies;
import com.udacity.sanketbhat.bakingapp.api.RecipeService;
import com.udacity.sanketbhat.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("WeakerAccess")
public class MainViewModel extends AndroidViewModel {

    private final RecipeService recipeService;
    private final MutableLiveData<List<Recipe>> recipeLiveData;
    private final MutableLiveData<Void> errorIndicator;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recipeService = Dependencies.getRecipeService();
        recipeLiveData = new MutableLiveData<>();
        errorIndicator = new MutableLiveData<>();
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        if (recipeLiveData.getValue() == null) {
            recipeService.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    if (response.isSuccessful()) {
                        recipeLiveData.setValue(response.body());
                    } else {
                        errorIndicator.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    errorIndicator.setValue(null);
                }
            });
        }
        return recipeLiveData;
    }


    public MutableLiveData<Void> getErrorIndicator() {
        return errorIndicator;
    }
}
