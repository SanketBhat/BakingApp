package com.udacity.sanketbhat.bakingapp.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * The configuration screen for the {@link RecipeIngredients RecipeIngredients} AppWidget.
 */
public class RecipeIngredientsConfigureActivity extends Activity {

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Intent intent1 = getIntent();
        Bundle extras = intent1.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(MainActivity.ACTION_PICK_RECIPE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ArrayList<Ingredient> ingredients = data.getParcelableArrayListExtra(MainActivity.SELECTED_RECIPE_INGREDIENTS);
            Gson gson = new Gson();
            String resultString = gson.toJson(ingredients);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            pref.edit()
                    .putString(String.valueOf(mAppWidgetId), resultString)
                    .commit();

            new Handler().postDelayed(() -> {
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }, 3000);
            Toast.makeText(this, "Adding home screen widget", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}

