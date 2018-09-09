package com.udacity.sanketbhat.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.adapter.StepListAdapter;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;
import com.udacity.sanketbhat.bakingapp.model.Recipe;
import com.udacity.sanketbhat.bakingapp.model.Step;

import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity implements StepListAdapter.StepClickListener {
    List<Step> steps;
    StepListAdapter adapter;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // TODO: 31-08-2018 Select this for the home screen widget
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        Bundle bundle = getIntent().getExtras();

        // TODO: 31-08-2018 Handle NullPointerException
        if (bundle != null) {
            Recipe recipe = bundle.getParcelable(MainActivity.RECIPE_ITEM);
            toolbar.setTitle(recipe.getName());

            steps = recipe.getSteps();
            List<Ingredient> ingredients = recipe.getIngredients();
        }

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, steps);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<Step> stepList) {
        adapter = new StepListAdapter(stepList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStepClick(View v, int position) {
        Step step = adapter.getStepList().get(position);
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepDetailFragment.STEP_ITEM, step);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailFragment.STEP_ITEM, step);

            startActivity(intent);
        }
    }
}
