package com.udacity.sanketbhat.bakingapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Recipe;
import com.udacity.sanketbhat.bakingapp.utils.Utils;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private List<Recipe> recipeList;
    private final RecipeClickListener mRecipeClickListener;
    private final Context mContext;
    private final int[] recipeImageIds;

    public RecipeListAdapter(List<Recipe> recipes, Context context, RecipeClickListener listener) {
        this.mRecipeClickListener = listener;
        this.recipeList = recipes;
        this.mContext = context;
        recipeImageIds = Utils.getRecipeImageIds();
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);
        int steps = recipe.getSteps().size();
        int ingredients = recipe.getIngredients().size();
        int servings = recipe.getServings();
        Resources res = mContext.getResources();

        // Using static images, If thumbnail url is provided that will be displayed...
        holder.recipeImage.setImageResource(recipeImageIds[position % recipeImageIds.length]);
        if (recipeList.get(position).getImage() != null && !recipeList.get(position).getImage().equals("")) {
            Picasso.with(holder.itemView.getContext())
                    .load(Uri.parse(recipeList.get(position).getImage()))
                    .into(holder.recipeImage);
        }
        holder.recipeName.setText(recipeList.get(position).getName());
        holder.recipeSteps.setText(res.getQuantityString(R.plurals.recipe_list_item_steps, steps, steps));
        holder.recipeServings.setText(res.getQuantityString(R.plurals.recipe_list_item_servings, servings, servings));
        holder.recipeIngredients.setText(res.getQuantityString(R.plurals.recipe_list_item_ingredients, ingredients, ingredients));
    }

    @Override
    public int getItemCount() {
        if (recipeList != null) return recipeList.size();
        return 0;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public interface RecipeClickListener {
        void onRecipeClick(int position);
    }

    class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        final ImageView recipeImage;
        final TextView recipeName, recipeIngredients, recipeServings, recipeSteps;
        final View revealView;
        int x, y;

        RecipeListViewHolder(View itemView) {
            super(itemView);
            itemView.setOnTouchListener(this);
            itemView.setOnClickListener(this);
            recipeImage = itemView.findViewById(R.id.recipeImageView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeIngredients = itemView.findViewById(R.id.recipeIngredientsCount);
            recipeServings = itemView.findViewById(R.id.recipeServingsCount);
            recipeSteps = itemView.findViewById(R.id.recipeStepsCount);
            revealView = itemView.findViewById(R.id.revealEffectView);
        }

        @Override
        public void onClick(View v) {
            Utils.animateReveal(revealView, x, y);
            if (mRecipeClickListener != null)
                mRecipeClickListener.onRecipeClick(getAdapterPosition());
        }


        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            x = (int) event.getX();
            y = (int) event.getY();
            // Let the system handle all other tasks
            return false;
        }
    }

}
