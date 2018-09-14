package com.udacity.sanketbhat.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepIngredientsActivity extends AppCompatActivity {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_ingredients);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.ingredients_activity_title);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        //savedInstanceState not null implies state saved by fragment
        if (savedInstanceState == null) {
            //Put the ingredient list and add the fragment
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
