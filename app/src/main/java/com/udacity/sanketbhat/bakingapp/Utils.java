package com.udacity.sanketbhat.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.List;

public class Utils {

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
}
