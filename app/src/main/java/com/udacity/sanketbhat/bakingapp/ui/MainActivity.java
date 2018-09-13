package com.udacity.sanketbhat.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.Utils;
import com.udacity.sanketbhat.bakingapp.adapter.RecipeLayoutManager;
import com.udacity.sanketbhat.bakingapp.adapter.RecipeListAdapter;
import com.udacity.sanketbhat.bakingapp.model.Ingredient;
import com.udacity.sanketbhat.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeClickListener {

    public static final String ACTION_PICK_RECIPE = "pickRecipe";
    public static final String SELECTED_RECIPE_INGREDIENTS = "recipeIngredients";
    public static final String RECIPE_ITEM = "RecipeItem";
    public static final String SELECTED_RECIPE_NAME = "recipeName";
    @BindView(R.id.activity_main_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recipeRecyclerView)
    RecyclerView recipeRecyclerView;
    private ActionMode actionMode = null;
    private RecipeListAdapter listAdapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        //Prepare Views
        initializeRecyclerView();

        //Observer for the recipe list
        viewModel.getRecipes().observe(this, recipes -> {
            listAdapter.setRecipeList(recipes);
            progressBar.setVisibility(View.GONE);
        });

        //Called when some error occurs
        viewModel.getErrorIndicator().observe(this, aVoid -> {
            progressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main_view), R.string.activity_main_error_message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.activity_main_snackbar_retry_button, v -> {
                progressBar.setVisibility(View.VISIBLE);
                viewModel.getRecipes();
            });
            snackbar.show();
        });

        if (getIntent() != null && getIntent().getAction() != null && getIntent().getAction().equals(ACTION_PICK_RECIPE)) {
            //Activity launched for selecting recipe for home screen widget
            setResult(RESULT_CANCELED);
            //Start actionMode so that user can pick the recipe for home screen widget
            startSupportActionMode(new ActionModeCallback());
        }
    }

    private void initializeRecyclerView() {
        //Initialize adapter with null list, once list is loaded it will be swapped
        listAdapter = new RecipeListAdapter(null, this, this);

        //RecipeLayoutManager automatically decides the span size based on screen width
        recipeRecyclerView.setLayoutManager(new RecipeLayoutManager(this));

        recipeRecyclerView.setAdapter(listAdapter);
        recipeRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onRecipeClick(int position) {
        Recipe recipe = listAdapter.getRecipeList().get(position);

        if (actionMode == null) {
            //Normal, perform the click and launch step's activity with the clicked item
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_ITEM, recipe);

            Intent intent = new Intent(this, StepListActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            //It is for home screen widget, put name and ingredients as extras and finish
            List<Ingredient> ingredients = recipe.getIngredients();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(SELECTED_RECIPE_NAME, recipe.getName());
            resultIntent.putExtra(SELECTED_RECIPE_INGREDIENTS, Utils.getJsonStringFromList(ingredients));
            setResult(RESULT_OK, resultIntent);

            finish();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle(getString(R.string.activity_main_action_mode_title));
            actionMode = mode;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            finish();
        }
    }
}
