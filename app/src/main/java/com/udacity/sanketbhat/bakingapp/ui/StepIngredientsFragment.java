package com.udacity.sanketbhat.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.adapter.IngredientListAdapter;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class StepIngredientsFragment extends Fragment {

    public static final String EXTRA_INGREDIENT_LIST = "ingredientsList";
    private ArrayList<Ingredient> ingredients;

    public StepIngredientsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = getArguments().getParcelableArrayList(EXTRA_INGREDIENT_LIST);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_step_ingredients, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.ingredientsRecyclerView);
        IngredientListAdapter adapter = new IngredientListAdapter(ingredients);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}
