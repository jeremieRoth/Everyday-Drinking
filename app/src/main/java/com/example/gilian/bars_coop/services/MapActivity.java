package com.example.gilian.bars_coop.services;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.os.Build;

import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.services.Exemples.EstablishmentDialog;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationListener;
import com.mapquest.mapping.MapQuestAccountManager;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;


import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.drawable;
import static android.R.attr.permission;

public class MapActivity extends AppCompatActivity {
    private String login = "jixalas" ;
    private String motDePasse = "BxeLisE23G";
    private String base = login + ":" + motDePasse;
    private String authHeader;

    User user;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private Retrofit retrofit;

    private MapView mMapView;
    private MapboxMap nMapboxmap;
    private LocationManager lm;
    private List<Establishment> establishmentList;


    @Override
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuestAccountManager.start(getApplicationContext());

        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        lm= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        user = (User) extra.getParcelable("user");

        //getMap
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                nMapboxmap=mapboxMap;
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return  ;
                }
                if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    nMapboxmap.setMyLocationEnabled(true);
                    Toast.makeText(MapActivity.this, "GPS activer ", Toast.LENGTH_SHORT).show();
                    Location location= lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Log.d("GPSBis","longitude : "+location.getLongitude()+"latitude : "+location.getLatitude() );
                    nMapboxmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),14));
                }else {
                    nMapboxmap.setMyLocationEnabled(false);
                    Toast.makeText(MapActivity.this, "GPS desactiver ", Toast.LENGTH_SHORT).show();
                }


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12000, 0, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d("GPS","longitude : "+location.getLongitude()+"latitude : "+location.getLatitude() );

                        //nMapboxmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),14));
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                        Log.d("StatusChange","status : "+s+" int : "+i );
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        Log.d("onProviderEnabled","msg : "+s );
                        nMapboxmap.setMyLocationEnabled(true);
                        Toast.makeText(MapActivity.this, "GPS activer ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        Log.d("onProviderDisabled","msg : "+s );
                        nMapboxmap.setMyLocationEnabled(false);
                        Toast.makeText(MapActivity.this, "GPS desactiver ", Toast.LENGTH_SHORT).show();
                    }
                });


                nMapboxmap.setOnMapClickListener(new com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull final LatLng latLng) {
                        //Toast.makeText(MapActivity.this, "Click latitude : "+latLng.getLatitude()+" longitude : "+latLng.getLongitude() , Toast.LENGTH_SHORT).show();

                        Log.d("LongPress", "latitude : "+latLng.getLatitude()+" longitude : "+latLng.getLongitude());
                        final AddEstablishmentDialog addEstablishmentDialog=new AddEstablishmentDialog(MapActivity.this);
                        addEstablishmentDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MapActivity.this, "Click cancel", Toast.LENGTH_SHORT).show();
                                addEstablishmentDialog.hide();
                            }
                        });
                        addEstablishmentDialog.getOkButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String name = String.valueOf(addEstablishmentDialog.getNameEstablishment().getText());
                                boolean nameUsed=false;
                                if(establishmentList==null){
                                    establishmentList=new ArrayList<Establishment>();
                                }
                                if(!establishmentList.isEmpty()) {
                                    for (Establishment establishment : establishmentList) {
                                        if (name.equals(establishment.getName()) ){
                                            nameUsed=true;
                                        }
                                    }
                                }
                                if(name!="" && nameUsed==false){
                                    Toast.makeText(MapActivity.this, "Click ok, Bar:"+name, Toast.LENGTH_SHORT).show();
                                    addMarker(nMapboxmap,latLng, name);

                                    Establishment establishment=new Establishment();
                                    establishment.setName(name);
                                    com.example.gilian.bars_coop.Entity.Location locationEstablishment=new com.example.gilian.bars_coop.Entity.Location();
                                    locationEstablishment.setLatitude(Double.toString(latLng.getLatitude()));
                                    locationEstablishment.setLongitude(Double.toString(latLng.getLongitude()));
                                    establishment.setLocation(locationEstablishment);
                                    establishmentList.add(establishment);
                                }
                                else{
                                    Toast.makeText(MapActivity.this, "name empty or used", Toast.LENGTH_SHORT).show();
                                }




                                addEstablishmentDialog.hide();
                            }
                        });
                        addEstablishmentDialog.show();

                    }
                });
//                nMapboxmap.setOnMapLongClickListener(new com.mapbox.mapboxsdk.maps.MapboxMap.OnMapLongClickListener() {
//                    @Override
//                    public void onMapLongClick(@NonNull LatLng latLng) {
//                        Toast.makeText(MapActivity.this, "LongClick latitude : "+latLng.getLatitude()+" longitude : "+latLng.getLongitude(), Toast.LENGTH_SHORT).show();
//                        Log.d("LongPress", "latitude : "+latLng.getLatitude()+" longitude : "+latLng.getLongitude());
//                        addMarker(nMapboxmap,new LatLng(latLng.getLatitude(),latLng.getLongitude()), "LongClick");
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
//                        builder.setMessage("tutu")
//                                .setTitle("zzzz");
//                        AlertDialog dialog = builder.create();
//                        //dialog.show();
//                    }
//                });
                initRetrofit();
                MapActivity.this.getEstablishments().enqueue(new Callback<List<Establishment>>() {
                    @Override
                    public void onResponse(Call<List<Establishment>> call, Response<List<Establishment>> response) {
                        if(response.isSuccessful()){
                            Log.d("API REST","Establishment Sa fonctionne");
                            establishmentList= response.body();
                            for (Establishment establishment:establishmentList) {
                                Log.d("Establishment","name : "+establishment.getName()+" latitude : "+establishment.getLocation().getLatitude()+" longitude : "+establishment.getLocation().getLongitude());
                                LatLng latLngEstablishment=new LatLng(new LatLng(Double.valueOf(establishment.getLocation().getLatitude()),Double.valueOf(establishment.getLocation().getLongitude())));
                                addMarker(mapboxMap,latLngEstablishment,establishment.getName());
                            }
                        }else {
                            Log.d("API REST",response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Establishment>> call, Throwable t) {
                        Log.d("API REST","Establishment Sa fonctionne pas");
                        Log.d("API REST",t.toString());
                    }
                });

                nMapboxmap.setOnInfoWindowClickListener(new com.mapbox.mapboxsdk.maps.MapboxMap.OnInfoWindowClickListener() {
                    @Override
                    public boolean onInfoWindowClick(@NonNull Marker marker) {
                        //Toast.makeText(MapActivity.this, "window click", Toast.LENGTH_SHORT).show();
                        if(MapActivity.this.establishmentList!=null){
                            String name = marker.getTitle();
                            Establishment establishmentRecup=null;
                            for (Establishment establishment: MapActivity.this.establishmentList){
                                if(establishment.getName().equals(name)){
                                    establishmentRecup=establishment;
                                }
                            }

                            EstablishmentDialog establishmentDialog = new EstablishmentDialog(MapActivity.this,establishmentRecup);
                            establishmentDialog.show();
                        }else {
                            Toast.makeText(MapActivity.this, "Base de donnée inaccessible", Toast.LENGTH_SHORT).show();
                        }




                        return false;
                    }
                });

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

    public void initRetrofit()//Initialise retrofit obligatoire pour effectuer une requette
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.16/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();


    }


    public Call<List<Establishment>> getEstablishments(){
        EstablishmentService establishmentService = retrofit.create(EstablishmentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Establishment>> call = establishmentService.getEstablishments(authHeader);

        return call;
    }

    public boolean putEstablishment(){

        return false;
    }

    public  void addMarker(MapboxMap mapboxMap, LatLng latLng, String name){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(name);
        markerOptions.snippet(getResources().getString(R.string.marker_msg));
        mapboxMap.addMarker(markerOptions);
    }


}
