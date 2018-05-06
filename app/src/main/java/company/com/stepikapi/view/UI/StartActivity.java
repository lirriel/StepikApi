package company.com.stepikapi.view.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.List;

import company.com.stepikapi.model.Api.AppDelegate;
import company.com.stepikapi.model.Entity.Course;
import company.com.stepikapi.model.Entity.Search;
import company.com.stepikapi.R;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragment.OnListFragmentInteractionListener,
        FaveFragment.OnListFragmentInteractionListener {
    private MaterialSearchView searchView;
    private AppDelegate appDelegate;
    private List<Course> courses;
    private Fragment fragment = null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
        setListeners();
    }

    private void displaySelectedScreen(int itemId) {
        switch (itemId) {
            case R.id.nav_search:
                fragment = new ItemFragment();
                break;
            case R.id.nav_fave:
                fragment = new FaveFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Load search request from net using api
     * @param searchTxt
     */
    @SuppressLint("CheckResult")
    private void loadSearch(String searchTxt) {
        appDelegate
                .getApiService()
                .getSearch(searchTxt)
                .enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(@NonNull Call<Search> call, @NonNull Response<Search> response) {
                        findViewById(R.id.error_layout).setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        Search search = response.body();
                        courses = search.getCourseList();

                        // if courses not found
                        if (courses.size() == 0) {
                            findViewById(R.id.error_layout).setVisibility(View.VISIBLE);
                            fragment.getView().setVisibility(View.GONE);
                        }
                        else {
                            // show fragment with list of courses
                            fragment = ItemFragment.newInstance(1, courses);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("list", (Serializable) courses);
                            fragment.setArguments(bundle);

                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                            Log.v("transaction", "commit");
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Log.v("search", "failed to load");
                        progressBar.setVisibility(View.GONE);
                        findViewById(R.id.error_layout).setVisibility(View.VISIBLE);

                        if (fragment != null)
                            fragment.getView().setVisibility(View.GONE);
                    }
                });
    }

    private void setListeners() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                loadSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                searchView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Initialize activity with variables
     */
    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchView = findViewById(R.id.search_view);

        // appdelegate for api request
        appDelegate = AppDelegate.from(getApplicationContext());

        // progress bar
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onListFragmentInteraction(int item) {
        Log.v("item", "click");
    }
}
