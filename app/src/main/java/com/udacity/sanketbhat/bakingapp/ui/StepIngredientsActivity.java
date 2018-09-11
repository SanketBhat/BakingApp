package com.udacity.sanketbhat.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class StepIngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_ingredients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra(StepIngredientsFragment.EXTRA_INGREDIENT_LIST);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(StepIngredientsFragment.EXTRA_INGREDIENT_LIST, ingredients);
            StepIngredientsFragment fragment = new StepIngredientsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_ingredient_container, fragment)
                    .commit();
        }
    }

}
