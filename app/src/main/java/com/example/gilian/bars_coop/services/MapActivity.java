package com.example.gilian.bars_coop.services;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.os.Build;

import com.mapquest.mapping.MapQuestAccountManager;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;


import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.R;

import static android.R.attr.permission;

public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    private MapboxMap nMapboxmap;
    private LocationManager lm;
    @Override
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        lm= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationManager.GPS_PROVIDER;
        /*Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(true);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setSpeedRequired(false);
        String provider = lm.getBestProvider(criteria, false);*/




        android.location.Location location = lm.getLastKnownLocation(provider);
        Log.i("MapActivity", "Le provider "+ provider);
        if(location != null){
            Log.d("MapActivity", "longitude : "+location.getLongitude()+"latitude : "+location.getLatitude()+"Altitude : "+location.getAltitude());
        }
        /*int internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, permission);
        }
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permission);
        }*/
        MapQuestAccountManager.start(getApplicationContext());

        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);




        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        User user = (User) extra.getParcelable("user");

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                nMapboxmap=mapboxMap;
                if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    nMapboxmap.setMyLocationEnabled(true);
                    Toast.makeText(MapActivity.this, "GPS activer ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MapActivity.this, "GPS desactiver ", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
