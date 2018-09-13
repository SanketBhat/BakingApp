package com.udacity.sanketbhat.bakingapp;

import com.udacity.sanketbhat.bakingapp.api.RecipeService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dependencies {
    private static final long CONNECTION_TIMEOUT = 10000;
    private static final long READ_TIMEOUT = 15000;
    private static RecipeService recipeService = null;
    private static final String NETWORK_CONTENT_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static synchronized RecipeService getRecipeService() {
        if (recipeService == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NETWORK_CONTENT_BASE_URL)
                    .client(okHttpClient)
                    .build();
            recipeService = retrofit.create(RecipeService.class);
        }
        return recipeService;
    }

}
