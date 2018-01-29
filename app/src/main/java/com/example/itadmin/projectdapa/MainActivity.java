package com.example.itadmin.projectdapa;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.itadmin.projectdapa.maps.ListFragment;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{

    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation= (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();
    }
    private void createNavItems()
    {
        //CREATE ITEMS
        AHBottomNavigationItem crimeItem=new AHBottomNavigationItem("TAB 1", R.drawable.background);
        AHBottomNavigationItem dramaItem=new AHBottomNavigationItem("TAB 2",R.drawable.background);
        AHBottomNavigationItem docstem=new AHBottomNavigationItem("SURVIVAL",R.drawable.background);

        //ADD THEM to bar
        bottomNavigation.addItem(crimeItem);
        bottomNavigation.addItem(dramaItem);
        bottomNavigation.addItem(docstem);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        //set current item
        bottomNavigation.setCurrentItem(0);

    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        //show fragment
        if (position==0)
        {
            ListFragment listFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, listFragment).commit();
        }else  if (position==1)
        {
            ListFragment listFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, listFragment).commit();
        }else  if (position==2)
        {
            ListFragment listFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id, listFragment).commit();
        }
        return true;
    }
}
