package com.udacity.sanketbhat.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        public IngredientViewHolder(View itemView) {
            super(itemView);
        }
    }
}
