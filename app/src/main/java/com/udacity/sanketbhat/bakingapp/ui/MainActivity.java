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

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeClickListener {

    public static final String ACTION_PICK_RECIPE = "pickRecipe";
    public static final String SELECTED_RECIPE_INGREDIENTS = "recipeIngredients";
    public static final String RECIPE_ITEM = "RecipeItem";
    public static final String SELECTED_RECIPE_NAME = "recipeName";
    private ActionMode actionMode = null;
    private RecyclerView recipeRecyclerView;
    private RecipeListAdapter listAdapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);

        ProgressBar progressBar = findViewById(R.id.activity_main_progressbar);
        initializeRecyclerView();

        viewModel.getRecipes().observe(this, recipes -> {
            listAdapter.setRecipeList(recipes);
            progressBar.setVisibility(View.GONE);
        });

        viewModel.getErrorIndicator().observe(this, aVoid -> {
            progressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main_view), "Failed to get recipes!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("RETRY", v -> {
                progressBar.setVisibility(View.VISIBLE);
                viewModel.getRecipes();
            });
            snackbar.show();
        });

        if (getIntent() != null && getIntent().getAction() != null && getIntent().getAction().equals(ACTION_PICK_RECIPE)) {
            //Activity launched for selecting recipe for home screen widget
            setResult(RESULT_CANCELED);
            startSupportActionMode(new ActionModeCallback());
        }
    }

    private void initializeRecyclerView() {
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        listAdapter = new RecipeListAdapter(null, this, this);
        recipeRecyclerView.setLayoutManager(new RecipeLayoutManager(this));
        recipeRecyclerView.setAdapter(listAdapter);
        recipeRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecipeClick(View v, int position) {
        Recipe recipe = listAdapter.getRecipeList().get(position);
        if (actionMode == null) {
            Intent intent = new Intent(this, StepListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(RECIPE_ITEM, recipe);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Intent resultIntent = new Intent();
            List<Ingredient> ingredients = recipe.getIngredients();
            resultIntent.putExtra(SELECTED_RECIPE_NAME, recipe.getName());
            resultIntent.putExtra(SELECTED_RECIPE_INGREDIENTS, Utils.getJsonStringFromList(ingredients));
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Select a recipe");
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
