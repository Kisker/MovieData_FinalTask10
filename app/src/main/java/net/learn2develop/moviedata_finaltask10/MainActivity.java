package net.learn2develop.moviedata_finaltask10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import net.learn2develop.moviedata_finaltask10.ORMLight.DatabaseHelper;
import net.learn2develop.moviedata_finaltask10.adapters.DrawerAdapter;
import net.learn2develop.moviedata_finaltask10.adapters.SearchAdapter;
import net.learn2develop.moviedata_finaltask10.dialogs.DialogAbout;
import net.learn2develop.moviedata_finaltask10.fragments.SettingsFragment;
import net.learn2develop.moviedata_finaltask10.model.Result;
import net.learn2develop.moviedata_finaltask10.model.Search;
import net.learn2develop.moviedata_finaltask10.navigation.NavigationItem;
import net.learn2develop.moviedata_finaltask10.net.RESTService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SearchAdapter adapter;
    private RecyclerView searchResult;
    private Toolbar toolbar;
    private RelativeLayout drawerPane;
    private ActionBarDrawerToggle drawerToggle;
    //setupDrawerItems()
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    //onDrawerOpen and onDraweClosed = setUpDrawer
    private CharSequence drawerTitle;
    private CharSequence title;

    private DatabaseHelper databaseHelper;

    public static String KEY = "KEY";

    private AlertDialog dialog;

    private final ArrayList<NavigationItem> navigationItems = new ArrayList<>();

    private void callService(String query) {
        HashMap<String, String> queryParams = new HashMap<>();
        //Bez ovih parametara ne bismo mogli pokrenuti Search
        queryParams.put("apikey", RESTService.API_KEY);
        queryParams.put("s", query);

        Call<Result> call = RESTService.apiInterface().getMoviesByTitle(queryParams);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.code() == 200) {
                    Result resp = response.body();
                    if (resp != null) {
                        //getSearch dobijemo iz Result-a!
                        adapter.addItems(resp.getSearch());

                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        setContentView(R.layout.activity_main);
        adapter = new SearchAdapter();

        searchResult = findViewById(R.id.searchResult);
        searchResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchResult.setItemAnimator(new DefaultItemAnimator());
        //bez poziva na setAdapter, klikom na Search nista ne bismo mogli dobiti, ni jedan rezultat pretrage
        searchResult.setAdapter(adapter);
        //***Kada kreiramo RecyclerTouchListener i DetailsActivity, onda tek pristupamo pozivu ove metode kako bi
        // nam otvorila drugi prozor
        searchResult.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), searchResult, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //sa pozivom na adapter.get(i) mi u stvari pozivamo pozivamo na kreiranu metodu Search unutar klase
                //SearchAdapter, kako bi nam omogucio da se vidi detaljan ispis fima u drugom prozoru!
                Search movie = adapter.get(position);
                //Bez pozivanja na Intent - Main i Details Activity - kada ispise nas upit filmova, klikom na odredjeni
                //film ne bi se otvorio drugi prozor (DetailsActivity) sa detaljima jednog filma
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                //movie.getImdbID() je povezan sa metodom Search (unutar SearchAdapter) i samom klasom Search!!!
                i.putExtra(KEY, movie.getImdbID());
                //startActivity je bitan u ovome, jer upravo on nam poziva otvaranje drugog prozora!
                //obvezno, ako zelimo pozvati startActivity, to moramo deklarisati u AndroidManifestu (.DetailsActivity)!!!
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //Bez implementacije EditText i poziva upita, ne bismo dobili izlistano-trazene upite klikom na Search!
        //Ovo se kreira unutar onClick interface !
        final EditText query = findViewById(R.id.searchText);

        Button search = findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Unese Batman -> query.getText().toString().trim()
                //pozivamo unapred kreirani CallService, kako bismo dobili natrag podatke!
                callService(query.getText().toString().trim());
            }
        });


        navigationItems.add(new NavigationItem("Search for Movies", "Go to Search page", R.drawable.ic_baseline_image_search_24));
        navigationItems.add(new NavigationItem("My Movies", "Go to My Movies page", R.drawable.ic_baseline_favorite_border_24));
        navigationItems.add(new NavigationItem("Settings", "Configuration", R.drawable.ic_baseline_settings_applications_24));
        navigationItems.add(new NavigationItem("Delete", "Delete Your Favorite Movies", R.drawable.ic_baseline_delete_outline_24));

        title = drawerTitle = getTitle();

        drawerLayout = findViewById(R.id.drawerLayout);
        drawerList = findViewById(R.id.navList);


        // Enable ActionBar app icon to behave as action to toggle nav drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }

    private void showAboutDialog() {
        AlertDialog dialog = new DialogAbout(this).prepareDialog();
        dialog.show();
    }


    //1. Kada kreiramo DatabaseHelper (treci korak), odmah je pozovemo u okviru MainActivity-ja!
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}