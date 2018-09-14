package com.udacity.sanketbhat.bakingapp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.ui.MainActivity;
import com.udacity.sanketbhat.bakingapp.utils.Utils;

/**
 * The configuration screen for the {@link RecipeIngredients RecipeIngredients} AppWidget.
 */
public class RecipeIngredientsConfigureActivity extends Activity {

    private int mAppWidgetId;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Intent intent1 = getIntent();
        Bundle extras = intent1.getExtras();
        mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
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

            String ingredients = data.getStringExtra(MainActivity.SELECTED_RECIPE_INGREDIENTS);
            String recipeName = data.getStringExtra(MainActivity.SELECTED_RECIPE_NAME);
            Utils.saveIngredientsList(getApplicationContext(), ingredients, mAppWidgetId);
            Utils.saveRecipeName(getApplicationContext(), recipeName, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);

            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{mAppWidgetId});
            sendBroadcast(intent);

            Toast.makeText(this, R.string.recipe_widget_message, Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}

