package com.example.gilian.bars_coop.services.Exemples;

import android.util.Base64;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.Location;
import com.example.gilian.bars_coop.services.LocationService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gilian on 15/08/2017.
 */

public class LocationExemple {
    private ListView listView;
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;
    private Retrofit retrofit;

    private List<Location> locations;
    private Location location;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public LocationExemple(){
        this.initRetrofit();

    }
    public void initRetrofit()//Initialise retrofit obligatoire pour effectuer une requette
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.254/git/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();


    }
    public void getLocations()//Récupère toutes les entités
    {
        LocationService locationService = retrofit.create(LocationService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Location>> call = locationService.getLocations(authHeader);

        final LocationExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)

        call.enqueue(new Callback<List<Location>>() {

            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {//Fonction d'appel à l'api voir les services
                if (response.isSuccessful()) {
                    System.out.println("Locations Sa fonctionne");
                    List<Location> locations = response.body();
                    self.locations = locations;
                }else{
                    System.out.println(response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                // the network call was a failure
                System.out.println("Locations Sa fonctionne pas");
                System.out.println(t.toString());
            }
        });

    }
    public void getLocation(int id)
    {
        LocationService commentService = retrofit.create(LocationService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Location> call = commentService.getLocation(authHeader,id);

        final LocationExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<Location>() {

            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (response.isSuccessful()) {
                    System.out.println("Location Sa fonctionne");
                    Location location = response.body();
                    self.location = location;
                }else{
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                // the network call was a failure
                System.out.println("Location Sa fonctionne pas");
                System.out.println(t.toString());
            }

        });

    }
    public void addLocation(float longitude, float latitude)//Ajoute une entité en base de donnée
    {
        LocationService locationService = retrofit.create(LocationService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        locationService.addLocation(authHeader,longitude,latitude).enqueue(new Callback<Location>() {//Fonction d'appel à l'api voir les services

            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if(response.isSuccessful()){
                    System.out.println("Location envoyer");
                }

            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                System.out.println("Location Erreur.");
            }
        });

    }
}
