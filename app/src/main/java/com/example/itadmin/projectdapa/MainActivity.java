package com.example.itadmin.projectdapa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.itadmin.projectdapa.maps.controller.MapsFragment;
import com.example.itadmin.projectdapa.news.controller.TwitterFragment;
import com.example.itadmin.projectdapa.session.controller.LoginActivity;
import com.example.itadmin.projectdapa.survival.SurvivalFragment;
import com.example.itadmin.projectdapa.weather.controller.WeatherFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{

    private AHBottomNavigation bottomNavigation;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static Context contextOfApplication;
    public WeatherFragment weatherFragment = new WeatherFragment();
    private MapsFragment mapsFragment = new MapsFragment();
    private SurvivalFragment survivalFragment = new SurvivalFragment();
    private TwitterFragment twitterFragment = new TwitterFragment();
    private int tabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        //if not logged in, go to loginActivity
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        //Show toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create Bottom Navigation
        bottomNavigation = findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
    }
    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem tab1 = new AHBottomNavigationItem("Maps", R.drawable.mapsicon);
        AHBottomNavigationItem tab2 = new AHBottomNavigationItem("Weather",R.drawable.weathericon);
        AHBottomNavigationItem tab3 = new AHBottomNavigationItem("News",R.drawable.newsicon);
        AHBottomNavigationItem tab4 = new AHBottomNavigationItem("Survival",R.drawable.survivalicon);
        AHBottomNavigationItem tab5 = new AHBottomNavigationItem("Profile",R.drawable.profileicon);

        //ADD THEM to bar
        bottomNavigation.addItem(tab1);
        bottomNavigation.addItem(tab2);
        bottomNavigation.addItem(tab3);
        bottomNavigation.addItem(tab4);
        bottomNavigation.addItem(tab5);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#272727"));
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));

        //set current item
        bottomNavigation.setCurrentItem(2);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        tabPosition = position;
        //show fragment
        if (position==0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, mapsFragment).commit();
            getSupportActionBar().hide();
        }else  if (position==1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, weatherFragment).commit();
            getSupportActionBar().show();
        }else  if (position==2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, twitterFragment).commit();
            getSupportActionBar().hide();
        }else  if (position==3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, survivalFragment).commit();
            getSupportActionBar().hide();
        }else  if (position==4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, mapsFragment).commit();
            getSupportActionBar().hide();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            if (weatherFragment.isNetworkAvailable()) {
                weatherFragment.getTodayWeather();
                weatherFragment.getLongTermWeather();
            } else {
                Snackbar.make(weatherFragment.appView, getString(R.string.msg_connection_not_available), Snackbar.LENGTH_LONG).show();
            }
            return true;
        }else if (id == R.id.action_search) {
            weatherFragment.searchCities();
            return true;
        }else if(id == R.id.action_logout){
            mAuth.signOut();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

/*    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }*/
}
