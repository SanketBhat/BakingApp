package com.udacity.sanketbhat.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.adapter.RecipeLayoutManager;
import com.udacity.sanketbhat.bakingapp.adapter.StepListAdapter;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;
import com.udacity.sanketbhat.bakingapp.model.Recipe;
import com.udacity.sanketbhat.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
@SuppressWarnings("WeakerAccess")
public class StepListActivity extends AppCompatActivity implements StepListAdapter.StepClickListener {
    List<Step> steps;
    StepListAdapter adapter;
    List<Ingredient> ingredients;

    @BindView(R.id.step_list)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(MainActivity.RECIPE_ITEM)) {
            Recipe recipe = bundle.getParcelable(MainActivity.RECIPE_ITEM);
            if (recipe != null) {
                setTitle(recipe.getName());
                steps = recipe.getSteps();
                ingredients = recipe.getIngredients();
            }
        }

        mTwoPane = getResources().getBoolean(R.bool.tablet);

        setupRecyclerView(recyclerView, steps);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<Step> stepList) {
        adapter = new StepListAdapter(this, stepList, this);
        recyclerView.setLayoutManager(new RecipeLayoutManager(this, mTwoPane));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStepClick(int position) {

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            if (position == 0) {
                //Ingredient list item clicked
                arguments.putParcelableArrayList(StepIngredientsFragment.EXTRA_INGREDIENT_LIST, new ArrayList<>(ingredients));
                StepIngredientsFragment fragment = new StepIngredientsFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            } else {
                Step step = adapter.getStepList().get(position - 1);

                arguments.putParcelable(StepDetailFragment.STEP_ITEM, step);
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, fragment)
                        .commit();
            }
        } else {
            if (position == 0) {
                //Ingredient list item clicked
                Intent intent = new Intent(this, StepIngredientsActivity.class);
                intent.putExtra(StepIngredientsFragment.EXTRA_INGREDIENT_LIST, new ArrayList<>(ingredients));
                startActivity(intent);
            } else {
                Step step = adapter.getStepList().get(position - 1);
                Intent intent = new Intent(this, StepDetailActivity.class);
                intent.putExtra(StepDetailFragment.STEP_ITEM, step);

                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
