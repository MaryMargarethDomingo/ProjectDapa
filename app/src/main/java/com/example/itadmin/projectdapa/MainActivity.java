package com.example.itadmin.projectdapa;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(hasLocationPermission()){
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);

            startActivity(intent);
        }else{
            requestLocationPermission();
        }
    }

    private boolean hasLocationPermission(){
        int result = 0;

        String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};

        for(String permission: permissions){
            result = checkCallingOrSelfPermission(permission);

            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void requestLocationPermission(){
        String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //run intent
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                    startActivity(intent);
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Toast.makeText(this, "Location Access Denied! Current location couldn't be found.", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                }
                break;
        }
    }
}
