package com.udacity.sanketbhat.bakingapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.List;

public class Utils {

    private static final int[] static_images = {
            R.drawable.recipe_image_1,
            R.drawable.recipe_image_2,
            R.drawable.recipe_image_3,
            R.drawable.recipe_image_4
    };

    public static int[] getRecipeImageIds() {
        return static_images;
    }

    @SuppressLint("ApplySharedPref")
    public static void saveIngredientsList(Context context, String ingredientsJson, int mAppWidgetId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(String.valueOf(mAppWidgetId), ingredientsJson)
                .commit();
    }

    public static String getJsonStringFromList(List<Ingredient> ingredients) {
        Gson gson = new Gson();
        return gson.toJson(ingredients);
    }

    public static List<Ingredient> getSavedIngredients(Context context, int mAppWidgetId) {
        String jsonString = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(String.valueOf(mAppWidgetId), "");
        Gson gson = new Gson();
        return gson.fromJson(jsonString, new TypeToken<List<Ingredient>>() {
        }.getType());
    }

    @SuppressLint("ApplySharedPref")
    public static void saveRecipeName(Context context, String recipeName, int mAppWidgetId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("name_" + mAppWidgetId, recipeName)
                .commit();
    }

    public static String getRecipeName(Context context, int mAppWidgetId) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("name_" + mAppWidgetId, "");
    }

    public static void deleteNameAndList(Context context, int mAppWidgetId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (prefs.contains(String.valueOf(mAppWidgetId))) {
            prefs.edit()
                    .remove(String.valueOf(mAppWidgetId))
                    .apply();
        }
        if (prefs.contains("name_" + mAppWidgetId)) {
            prefs.edit()
                    .remove("name_" + mAppWidgetId)
                    .apply();
        }
    }

    public static void animateReveal(View revealView, int x, int y) {
        Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, 32, Math.max(revealView.getHeight(), revealView.getWidth()));

        revealView.setVisibility(View.VISIBLE);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                revealView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                revealView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                revealView.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();

    }
}
