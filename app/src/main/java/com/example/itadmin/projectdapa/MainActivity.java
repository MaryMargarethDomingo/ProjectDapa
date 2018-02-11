package com.example.itadmin.projectdapa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.itadmin.projectdapa.maps.MapsFragment;
import com.example.itadmin.projectdapa.session.LoginActivity;
import com.example.itadmin.projectdapa.weather.activities.WeatherFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{

    private AHBottomNavigation bottomNavigation;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static Context contextOfApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation= findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
    }
    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem tab1=new AHBottomNavigationItem("TAB 1", R.drawable.mapsicon);
        AHBottomNavigationItem tab2=new AHBottomNavigationItem("Weather",R.drawable.weathericon);
        AHBottomNavigationItem tab3=new AHBottomNavigationItem("Survival",R.drawable.survivalicon);

        //ADD THEM to bar
        bottomNavigation.addItem(tab1);
        bottomNavigation.addItem(tab2);
        bottomNavigation.addItem(tab3);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#272727"));
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));

        //set current item
        bottomNavigation.setCurrentItem(0);

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        //show fragment
        if (position==0)
        {
            MapsFragment mapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, mapsFragment).commit();
        }else  if (position==1)
        {
            WeatherFragment weatherFragment = new WeatherFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, weatherFragment).commit();
        }else  if (position==2)
        {
            MapsFragment mapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, mapsFragment).commit();
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

        if(id == R.id.action_logout){
            mAuth.signOut();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }


}
