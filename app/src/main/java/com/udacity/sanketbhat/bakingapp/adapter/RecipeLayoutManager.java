package com.udacity.sanketbhat.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;

public class RecipeLayoutManager extends GridLayoutManager {

    private static final int ITEM_WIDTH_MIN = 280;

    public RecipeLayoutManager(Context context) {
        super(context, 1);
        calculateDynamicSpan(context);
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
