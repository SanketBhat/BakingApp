package com.udacity.sanketbhat.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.Utils;
import com.udacity.sanketbhat.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RecipeIngredientsConfigureActivity RecipeIngredientsConfigureActivity}
 */
public class RecipeIngredients extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_grid);

        //Set recipe name and it's pending intent
        views.setTextViewText(R.id.ingredients_widget_recipe_name, Utils.getRecipeName(context, appWidgetId));
        Intent launchIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingLaunchIntent = PendingIntent.getActivity(context, 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ingredients_widget_recipe_name, pendingLaunchIntent);

        //Set class for widget list
        Intent intent = new Intent(context, IngredientListWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //If I don't set different intent data, previous RemoteViewsFactory is used
        //Strange!!
        intent.setData(Uri.parse("content://" + appWidgetId));
        views.setRemoteAdapter(R.id.ingredients_widget_grid_view, intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(Intent.ACTION_VIEW), 0);
        views.setPendingIntentTemplate(R.id.ingredients_widget_grid_view, pendingIntent);

        views.setEmptyView(R.id.ingredients_widget_grid_view, R.id.ingredients_widget_empty_text);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            Utils.deleteNameAndList(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        // Don't need this function
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        // Don't need this function
    }

}

