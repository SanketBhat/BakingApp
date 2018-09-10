package com.udacity.sanketbhat.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.Utils;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IngredientListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new ListRemoteViewsFactory(getApplicationContext(), id);
    }

}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private int mAppWidgetId;
    private Context context;
    private List<Ingredient> ingredients = new ArrayList<>();

    ListRemoteViewsFactory(Context context, int mAppWidgetId) {
        this.mAppWidgetId = mAppWidgetId;
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        ingredients = Utils.getSavedIngredients(context, mAppWidgetId);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_item);
        String name = ingredients.get(position).getIngredient();
        String iName = name.substring(0, 1).toUpperCase() + name.substring(1);
        String quantity = String.format(Locale.getDefault(), "%.1f %s", ingredients.get(position).getQuantity(), ingredients.get(position).getMeasure());
        views.setTextViewText(R.id.ingredient_widget_item_name, iName);
        views.setTextViewText(R.id.ingredient_widget_item_quantity, quantity);

        Intent fillInIntent = new Intent();
        fillInIntent.setData(Uri.parse("https://www.google.com/images?q=" + iName));
        views.setOnClickFillInIntent(R.id.ingredients_widget_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        //Only one type of view is present
        return 1;
    }

    @Override
    public long getItemId(int position) {
        //We don't need this method, just ignore
        return position;
    }

    @Override
    public boolean hasStableIds() {
        //We don't need this method, just ignore
        return true;
    }
}