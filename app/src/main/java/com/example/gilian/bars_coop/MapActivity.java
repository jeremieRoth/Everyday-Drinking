package com.example.gilian.bars_coop;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.os.Build;

import com.example.gilian.bars_coop.Entity.Comment;
import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.Entity.Event;
import com.example.gilian.bars_coop.services.CommentService;
import com.example.gilian.bars_coop.services.EstablishmentService;
import com.example.gilian.bars_coop.services.EventService;
import com.example.gilian.bars_coop.services.LocationService;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;



import com.example.gilian.bars_coop.Entity.User;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public List<Establishment> establishmentList;
    public List<Comment> commentList;
    public List<Event> eventList;

    public String name;
//    public String comment;
//    public String event;

    Establishment establishmentSave;
    AddCommentDialog addCommentDialog;
    AddEventDialog addEventDialog;
    AddEstablishmentDialog addEstablishmentDialog;
    EstablishmentDialog establishmentDialog;
    LatLng latLngStock;


    @Override
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MapQuestAccountManager.start(getApplicationContext());
        //getResources().getString
        Mapbox.getInstance(getApplicationContext(),getResources().getString(R.string.map_box_id));
        setContentView(R.layout.activity_map);

        mMapView = (MapView) findViewById(R.id.mapBoxMapView);
        mMapView.onCreate(savedInstanceState);
        lm= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        user = (User) extra.getParcelable("user");


        //getMapOnMapReadyCallback
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                nMapboxmap=mapboxMap;
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                        //ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission( MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return  ;
                }
                if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    nMapboxmap.setMyLocationEnabled(true);
                    Toast.makeText(MapActivity.this, "GPS activer ", Toast.LENGTH_SHORT).show();
                    Location location= lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Log.d("GPSBis","longitude : "+location.getLongitude()+"latitude : "+location.getLatitude() );
                    nMapboxmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),10));
                }else {
                    //nMapboxmap.setMyLocationEnabled(false);
                    Toast.makeText(MapActivity.this, "GPS desactiver ", Toast.LENGTH_SHORT).show();
                }


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 100, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //Log.d("GPS","longitude : "+location.getLongitude()+"latitude : "+location.getLatitude() );

                        nMapboxmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),10));
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                        Log.d("StatusChange","status : "+s+" int : "+i );
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        Log.d("onProviderEnabled","msg : "+s );
                        Long t1=System.currentTimeMillis();


                        nMapboxmap.setMyLocationEnabled(true);
                        while (System.currentTimeMillis()<t1+1000);
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
                    public void onMapClick(@NonNull  LatLng latLng) {
                        //Toast.makeText(MapActivity.this, "Click latitude : "+latLng.getLatitude()+" longitude : "+latLng.getLongitude() , Toast.LENGTH_SHORT).show();
                        MapActivity.this.latLngStock =latLng;
                        Log.d("LongPress", "latitude : "+latLng.getLatitude()+" longitude : "+latLng.getLongitude());
                        MapActivity.this.addEstablishmentDialog=new AddEstablishmentDialog(MapActivity.this);
                        addEstablishmentDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(MapActivity.this, "Click cancel", Toast.LENGTH_SHORT).show();
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
                                    //Toast.makeText(MapActivity.this, "Click ok, Bar:"+name, Toast.LENGTH_SHORT).show();

                                    postEstablishment(name,latLngStock.getLongitude(),latLngStock.getLatitude());

                                }
                                else{
                                    Toast.makeText(MapActivity.this, "champ vide ou nom utilisée", Toast.LENGTH_SHORT).show();
                                }




                                addEstablishmentDialog.hide();
                            }
                        });
                        addEstablishmentDialog.show();

                    }
                });

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
                                addMarker(MapActivity.this.nMapboxmap,latLngEstablishment,establishment.getName());
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
                            establishmentSave=null;
                            for (Establishment establishment: MapActivity.this.establishmentList){
                                if(establishment.getName().equals(name)){
                                    establishmentSave=establishment;
                                }
                            }
                            if(establishmentSave!=null) {

                                getComment().enqueue(new Callback<List<Comment>>() {
                                    @Override
                                    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                                        if (response.isSuccessful()) {
                                            MapActivity.this.commentList = response.body();
                                            Iterator itr = MapActivity.this.commentList.iterator();
                                            while (itr.hasNext()){
                                                Comment comment = (Comment) itr.next();
                                                if(comment.getEstablishment().getId()!=MapActivity.this.establishmentSave.getId()){
                                                    itr.remove();
                                                }

                                            }
                                            getEvent().enqueue(new Callback<List<Event>>() {
                                                @Override
                                                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                                                    if (response.isSuccessful()){
                                                        MapActivity.this.eventList = response.body();
                                                        Iterator itr = MapActivity.this.eventList.iterator();
                                                        while (itr.hasNext()){
                                                            Event event = (Event) itr.next();
                                                            if(event.getEstablishment().getId() != MapActivity.this.establishmentSave.getId()){
                                                                itr.remove();
                                                            }
                                                        }

                                                        MapActivity.this.establishmentDialog = new EstablishmentDialog(MapActivity.this.establishmentSave, MapActivity.this.commentList, MapActivity.this.eventList,MapActivity.this);

                                                        establishmentDialog.getAddComment().setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                MapActivity.this.addCommentDialog = new AddCommentDialog(MapActivity.this);
                                                                addCommentDialog.getOkButton().setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        String comment = String.valueOf(MapActivity.this.addCommentDialog.getCommentTxt().getText());

                                                                        float rate = MapActivity.this.addCommentDialog.getRatingBar().getRating();
                                                                        if (comment !=""){
                                                                            if (comment.length()<25){
                                                                                postComment(comment,establishmentSave,rate);
                                                                                addCommentDialog.hide();
                                                                            }else {
                                                                                Toast.makeText(MapActivity.this, "commentaire trop long", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            //Toast.makeText(MapActivity.this, comment, Toast.LENGTH_SHORT).show();


                                                                        }else {
                                                                            Toast.makeText(MapActivity.this, "champ vide", Toast.LENGTH_SHORT).show();
                                                                        }



                                                                    }
                                                                });
                                                                addCommentDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        addCommentDialog.hide();
                                                                    }
                                                                });

                                                                addCommentDialog.show();

                                                            }
                                                        });

                                                        establishmentDialog.getAddevent().setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {

                                                                MapActivity.this.addEventDialog = new AddEventDialog(MapActivity.this);

                                                                addEventDialog.getOkButton().setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        String eventText = String.valueOf(MapActivity.this.addEventDialog.getEventTxt().getText());
                                                                        if(eventText != ""){
                                                                            postEvent(eventText,establishmentSave);
                                                                            addEventDialog.hide();
                                                                        }else {
                                                                            Toast.makeText(MapActivity.this, "champ vide", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });

                                                                addEventDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        addEventDialog.hide();
                                                                    }
                                                                });
                                                                addEventDialog.show();
                                                            }
                                                        });





                                                        establishmentDialog.show();

                                                    }else {
                                                        Log.d("API REST not successful", "event not successful");

                                                        //Toast.makeText(MapActivity.this, "getEvent Failure", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<Event>> call, Throwable t) {
                                                    Log.d("API REST FAILURE", "event Sa fonctionne pas");
                                                    Log.d("API REST FAILURE", t.toString());
                                                    Toast.makeText(MapActivity.this, "erreur lors de la recuperation des event", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            Log.d("API REST successful", "Comment is successful ");
                                        } else {
                                            Log.d("API REST not successful", "Comment not successful");

                                            //Toast.makeText(MapActivity.this, "erreur lors de la recuperation des Comment", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Comment>> call, Throwable t) {
                                        Log.d("API REST FAILURE", "Comment Sa fonctionne pas");
                                        Log.d("API REST FAILURE", t.toString());
                                        Toast.makeText(MapActivity.this, "erreur lors de la recuperation des Comment", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            //EstablishmentDialog establishmentDialog = new EstablishmentDialog(MapActivity.this,establishmentRecup);
                            //establishmentDialog.show();
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
                .baseUrl("http://gilian.ddns.net/git/api_EverydayDrinking/web/")
                //.baseUrl("http://gilian.ddns.net/git/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();


    }


    public Call<List<Establishment>> getEstablishments(){
        EstablishmentService establishmentService = retrofit.create(EstablishmentService.class);

        Call<List<Establishment>> call = establishmentService.getEstablishments(authHeader);

        return call;
    }


    public Call<List<Comment>> getComment(){
        CommentService commentService = retrofit.create(CommentService.class);

        Call<List<Comment>> listCall =commentService.getComments(authHeader);

        return listCall;
    }
    public Call<List<Event>> getEvent() {
        EventService eventService = retrofit.create(EventService.class);

        Call<List<Event>> listCall = eventService.getEvents(authHeader);

        return listCall;
    }
    public void postEvent(String event, Establishment establishment){
        EventService eventService = retrofit.create(EventService.class);
        Log.d("PostEvent","event : "+event);
       // this.event = event;
        Call<Event> eventCall = eventService.addEvent(authHeader,event,establishment.getId());

        eventCall.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.isSuccessful()){
                    Event event1 = response.body();
                    MapActivity.this.establishmentDialog.updateEvent(event1);
                }else{
                    Log.d("responsEvent","response  not successful");
                    Toast.makeText(MapActivity.this, "event not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(MapActivity.this, "Base de donnée inaccessible event throw:"+t, Toast.LENGTH_SHORT).show();
                Log.d("EventOnFailure","erreur "+t);
            }
        });
    }
    public void postComment(String comment, Establishment establishment, float rate){
        CommentService commentService = retrofit.create(CommentService.class);
        Log.d("PostComment","user : "+this.user.getLogin()+" comment : "+comment);
        Call<Comment> commentCall = commentService.addComment(authHeader, comment, rate, this.user.getId(),establishment.getId());
        commentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){
                    Comment comment1 = response.body();
                    MapActivity.this.establishmentDialog.updateComment(comment1);

                }else{
                    Log.d("responsComment","response  not successful");
                    Toast.makeText(MapActivity.this, "comment not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(MapActivity.this, "Base de donnée inaccessible comment throw: "+t, Toast.LENGTH_SHORT).show();
                Log.d("CommentOnFailure","erreur "+t);
            }
        });
    }

    public void postEstablishment(String name, Double longitude, Double latitude){
        LocationService locationService = retrofit.create(LocationService.class);

        //authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        //final MapActivity self=this;
        Log.d("PostCall","location: "+latitude+" "+longitude);
        this.name = name;
        Call<com.example.gilian.bars_coop.Entity.Location> locationCall = locationService.addLocation(authHeader,longitude.floatValue(),latitude.floatValue());
        locationCall.enqueue(new Callback<com.example.gilian.bars_coop.Entity.Location>() {
            @Override
            public void onResponse(Call<com.example.gilian.bars_coop.Entity.Location> call, Response<com.example.gilian.bars_coop.Entity.Location> response) {
                if(response.isSuccessful()) {
                    com.example.gilian.bars_coop.Entity.Location location = response.body();
                    EstablishmentService establishmentService = MapActivity.this.retrofit.create(EstablishmentService.class);
                    Call<Establishment> establishmentCall = establishmentService.addEstablishment(MapActivity.this.authHeader,MapActivity.this.name,location.getId());
                    establishmentCall.enqueue(new Callback<Establishment>() {
                        @Override
                        public void onResponse(Call<Establishment> call, Response<Establishment> response) {
                            Log.d("testCalEsthablisement","pre bool");
                            if(response.isSuccessful()){
                                Log.d("testCalEsthablisement","post bool");
                                Establishment establishment= response.body();
                                MapActivity.this.establishmentList.add(establishment);
                                LatLng latLngEstha = new LatLng(Double.valueOf(establishment.getLocation().getLatitude()),Double.valueOf(establishment.getLocation().getLongitude()));
                                MapActivity.this.addMarker(MapActivity.this.nMapboxmap,latLngEstha,establishment.getName());
                            }else {
                                Log.d("testCalEsthablisement","post 2 bool");
                                Toast.makeText(MapActivity.this, "Base de donnée inaccessibleEsth", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Establishment> call, Throwable t) {
                            Toast.makeText(MapActivity.this, "Base de donnée inaccessibleEsth2 throw:"+t, Toast.LENGTH_SHORT).show();
                            Log.d("EstablishmentOnFailure","erreur"+t);
                        }
                    });
                    //addMarker(nMapboxmap,latLng, name);
                    //establishmentList.add(establishment);
                    Log.d("PostCall","location: "+location.getLatitude()+" "+location.getLongitude()+ " id "+location.getId());
                   // Toast.makeText(MapActivity.this, "location: "+location.getLatitude()+" "+location.getLongitude()+ " id "+location.getId(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MapActivity.this, "Base de donnée inaccessible", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.gilian.bars_coop.Entity.Location> call, Throwable t) {
                Toast.makeText(MapActivity.this, "Base de donnée inaccessible", Toast.LENGTH_SHORT).show();
            }
        });
       // new Callback<List<Establishment>>()
        //establishmentService.addEstablishment()
    }
    public  void addMarker(MapboxMap mapboxMap, LatLng latLng, String name){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(name);
        markerOptions.snippet(getResources().getString(R.string.marker_msg));
        mapboxMap.addMarker(markerOptions);
    }
}
