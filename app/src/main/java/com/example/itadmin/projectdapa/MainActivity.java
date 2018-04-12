package com.example.itadmin.projectdapa;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.itadmin.projectdapa.maps.controller.MapsFragment;
import com.example.itadmin.projectdapa.news.controller.TwitterFragment;
import com.example.itadmin.projectdapa.session.controller.LoginActivity;
import com.example.itadmin.projectdapa.session.controller.ProfileFragment;
import com.example.itadmin.projectdapa.session.utility.MovableFloatingActionButton;
import com.example.itadmin.projectdapa.session.utility.NotificationService;
import com.example.itadmin.projectdapa.survival.controller.SurvivalFragment;
import com.example.itadmin.projectdapa.weather.controller.WeatherFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener{

    private AHBottomNavigation bottomNavigation;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static Context contextOfApplication;
    public static WeatherFragment weatherFragment = new WeatherFragment();
    private MapsFragment mapsFragment = new MapsFragment();
    private SurvivalFragment survivalFragment = new SurvivalFragment();
    private TwitterFragment twitterFragment = new TwitterFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private int tabPosition;
    private MovableFloatingActionButton fab;


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

        //Set up Weather Notification Service
        notification(savedInstanceState);

        //Create Bottom Navigation
        bottomNavigation = findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Call 911?")
                        .setMessage("Do you really want to call 911?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(hasPhoneCallPermission()){
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:911"));

                                    if (ActivityCompat.checkSelfPermission(contextOfApplication, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    startActivity(callIntent);
                                }else{
                                    requestPhoneCallPermission();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

    private void notification(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 6); //starts at 0 //notif at 6 in the morning/
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);

        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (savedInstanceState == null) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
        }
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
        bottomNavigation.setCurrentItem(0);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        tabPosition = position;
        //show fragment
        if (position==0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.include2, mapsFragment).commit();
            getSupportActionBar().hide();
        }else  if (position==1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.include2, weatherFragment).commit();
            getSupportActionBar().show();
        }else  if (position==2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.include2, twitterFragment).commit();
            getSupportActionBar().hide();
        }else  if (position==3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.include2, survivalFragment).commit();
            getSupportActionBar().hide();
        }else  if (position==4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.include2, profileFragment).commit();
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

        /*if (id == R.id.action_refresh) {
            if (weatherFragment.isNetworkAvailable()) {
                weatherFragment.getTodayWeather();
                weatherFragment.getLongTermWeather();
            } else {
                Snackbar.make(weatherFragment.appView, getString(R.string.msg_connection_not_available), Snackbar.LENGTH_LONG).show();
            }
            return true;
        }else */if (id == R.id.action_search) {
            weatherFragment.searchCities();
            return true;
        }/*else if(id == R.id.action_logout){
            mAuth.signOut();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }*/

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


//permissions
    private static final int LOCATION_REQUEST_CODE = 1;
    private static final int CALL_PHONE_CODE = 2;

    @SuppressLint("WrongConstant")
    private boolean hasPhoneCallPermission(){
        int result = 0;

        String[] permissions = new String[]{android.Manifest.permission.CALL_PHONE};

        for(String permission: permissions){
            result = checkCallingOrSelfPermission(permission);

            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void requestPhoneCallPermission(){
        String[] permissions = new String[]{android.Manifest.permission.CALL_PHONE};

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, CALL_PHONE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    /*//run intent
                    Intent intent = new Intent(LoginActivity.this, WeatherFragment.class);
                    startActivity(intent);*/
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Toast.makeText(this, "Location Access Denied! Current location couldn't be found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case CALL_PHONE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    /*//run intent
                    Intent intent = new Intent(LoginActivity.this, WeatherFragment.class);
                    startActivity(intent);*/
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            Toast.makeText(this, "Phone call permission Denied! Can't call!.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

        }
    }
}
