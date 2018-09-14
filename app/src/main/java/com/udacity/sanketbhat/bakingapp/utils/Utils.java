package com.udacity.sanketbhat.bakingapp.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.google.gson.reflect.TypeToken;
import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.List;
import java.util.Locale;

/**
 * The class having various helpful methods.
 * Manages preference saves and retrieves, static image resource ids
 * and finally some string format methods
 */
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

    public static void saveIngredientsList(Context context, String ingredientsJson, int mAppWidgetId) {
        //Can not store in background, it is retrieved by widget immediately
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(String.valueOf(mAppWidgetId), ingredientsJson)
                .apply();
    }

    public static List<Ingredient> getSavedIngredients(Context context, int mAppWidgetId) {
        String jsonString = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(String.valueOf(mAppWidgetId), "");
        return JsonUtils.deserializeList(jsonString, new TypeToken<List<Ingredient>>() {
        }.getType());
    }

    public static void saveRecipeName(Context context, String recipeName, int mAppWidgetId) {
        //Can not store in background, it is retrieved by widget immediately
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString("name_" + mAppWidgetId, recipeName)
                .apply();
    }

    public static String getRecipeName(Context context, int mAppWidgetId) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString("name_" + mAppWidgetId, "");
    }

    public static void deleteNameAndList(Context context, int mAppWidgetId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //Delete json string
        if (prefs.contains(String.valueOf(mAppWidgetId))) {
            prefs.edit()
                    .remove(String.valueOf(mAppWidgetId))
                    .apply();
        }

        //Delete recipe name
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

    public static String getIngredientDisplayName(String ingredientName) {
        return ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1);
    }

    public static String getIngredientQuantityString(double quantity, String unit) {
        return String.format(Locale.getDefault(), "%.1f %s", quantity, unit);
    }
}
