package com.udacity.sanketbhat.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;

public class RecipeLayoutManager extends GridLayoutManager {

    private static final int ITEM_WIDTH_MIN = 280;

    public RecipeLayoutManager(Context context, boolean twoPane) {
        super(context, 1);
        if (!twoPane) {
            calculateDynamicSpan(context);
        }
    }

    public RecipeLayoutManager(Context context) {
        this(context, false);
    }

    private void calculateDynamicSpan(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        int actualDeviceWidth = (int) (widthPixels / metrics.density);
        int dynamicSpanCount = actualDeviceWidth / ITEM_WIDTH_MIN;
        if (dynamicSpanCount > 1)
            setSpanCount(dynamicSpanCount);
    }
}
