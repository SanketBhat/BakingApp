package com.udacity.sanketbhat.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    List<Ingredient> ingredients;

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
        String name = ingredients.get(position).getIngredient();
        String iName = name.substring(0, 1).toUpperCase() + name.substring(1);
        String quantity = String.format(Locale.getDefault(), "%.1f %s", ingredients.get(position).getQuantity(), ingredients.get(position).getMeasure());
        holder.name.setText(iName);
        holder.quantity.setText(quantity);
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) return ingredients.size();
        return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity;

        IngredientViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stepDetailIngredientName);
            quantity = itemView.findViewById(R.id.stepDetailIngredientQuantity);
        }
    }
}
