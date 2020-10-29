package com.example.babyinvestor;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.babyinvestor.api.ApiInterface;
import com.example.babyinvestor.api.YahooApiClient;
import com.example.babyinvestor.data.TrendingStocksAdapter;
import com.example.babyinvestor.data.model.TrendingTickers.Quotes;
import com.example.babyinvestor.data.model.TrendingTickers.TrendingTickers;
import com.example.babyinvestor.data.model.search.QueryResponse;
import com.example.babyinvestor.ui.fragments.News_fragment;
import com.example.babyinvestor.ui.fragments.Portfolio;
import com.example.babyinvestor.ui.fragments.Profile_fragment;
import com.example.babyinvestor.ui.fragments.Search_fragment;
import com.example.babyinvestor.ui.fragments.Stocks_fragment;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private EditText searchTxt;

    private ActionBarDrawerToggle drawerToggle;

    public static final String REGION = "US";
    public static final String LANG = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Search EditText
        searchTxt = (EditText)  findViewById(R.id.inputSearch);
        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Fragment fragment = new Search_fragment();
                Bundle args = new Bundle();
                args.putString("query", editable.toString());
                fragment.setArguments(args);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.commit();

               if(editable.toString().length() < 1){
                   Fragment frag = new Stocks_fragment();
                   FragmentTransaction transaction2 = fm.beginTransaction();
                   transaction2.replace(R.id.flContent, frag);
                   transaction2.commit();

               }


            }
        });

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        if(savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().replace(R.id.flContent,new Stocks_fragment()).commit();
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_stocks:
                fragmentClass = Stocks_fragment.class;
                break;
            case R.id.nav_portfolio:
                fragmentClass = Portfolio.class;
                break;
            case R.id.nav_news:
                fragmentClass = News_fragment.class;
                break;
            case R.id.nav_profile:
                fragmentClass = Profile_fragment.class;
                break;
            default:
                fragmentClass = Stocks_fragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.search,menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        final SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
//        MenuItem search = menu.findItem(R.id.search_bar);
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setQueryHint("Search Companies");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(query.length() > 2){
//
//                    Fragment fragment = new Search_fragment();
//                    Bundle args = new Bundle();
//                    args.putString("query", query);
//                    fragment.setArguments(args);
//
//                    FragmentManager fm = getSupportFragmentManager();
//                    FragmentTransaction transaction = fm.beginTransaction();
//                    transaction.replace(R.id.flContent, fragment);
//                    transaction.commit();
//
//                   // LoadJson(query);
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Fragment fragment = new Search_fragment();
//                Bundle args = new Bundle();
//                args.putString("query", newText);
//                fragment.setArguments(args);
//
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction transaction = fm.beginTransaction();
//                transaction.replace(R.id.flContent, fragment);
//                transaction.commit();
//                //LoadJson(newText);
//                return false;
//            }
//        });
//
//        search.getIcon().setVisible(false,false);
//
//
//        //return super.onCreateOptionsMenu(menu);
//        return true;
//    }

//    private void LoadJson(String keyword){
//        Log.d("DEBUG!!!!","Load Stoocks Json called");
//        ApiInterface apiInterface = YahooApiClient.getApiClient().create(ApiInterface.class);
//
//        Call<QueryResponse> call;
//        call = apiInterface.getQueryResults(LANG,REGION,keyword);
//
//        call.enqueue(new Callback<QueryResponse>() {
//            @Override
//            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
//                if(response.isSuccessful() && response.body().getResultSet().getResults()!=null){
//
//                    if(!quotes.isEmpty()){
//                        quotes.clear();
//                    }
//
//                    quotes = response.body().getTrendingTickerResults().getResults();
//                    Quotes q =quotes.get(0);
//                    quoteItems = q.getQuotes();
//                    // quoteItems = quotes.
//                    Log.d("DEBUG_size", String.valueOf(quoteItems.size()));
//                    adapter = new TrendingStocksAdapter(quoteItems,getActivity());
//                    adapter.setOnItemClickListener(Stocks_fragment.this);
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//
//                }
//                else{
//                    Toast.makeText(getActivity(),"No Result",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<QueryResponse> call, Throwable t) {
//                Log.d("FAILURE","failed");
//
//            }
//        });
//
//    }
}