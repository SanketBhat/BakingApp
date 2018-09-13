package com.udacity.sanketbhat.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.Utils;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private final List<Ingredient> ingredients;

    public IngredientListAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_detail_ingredient, parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.name.setText(Utils.getIngredientDisplayName(ingredient.getIngredient()));
        holder.quantity.setText(Utils.getIngredientQuantityString(ingredient.getQuantity(), ingredient.getMeasure()));
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) return ingredients.size();
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        final TextView name, quantity;

        IngredientViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stepDetailIngredientName);
            quantity = itemView.findViewById(R.id.stepDetailIngredientQuantity);
        }
    }
}
