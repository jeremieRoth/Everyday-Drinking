package com.example.gilian.bars_coop.services.Exemples;

import android.util.Base64;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.services.EstablishmentService;

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

public class EstablishmentExemple {
    private ListView listView;
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;
    private Retrofit retrofit;

    private List<Establishment> establishments;
    private Establishment establishment;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public EstablishmentExemple(){
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
    public void getEstablishments()//Récupère toutes les entités
    {
        EstablishmentService establishmentService = retrofit.create(EstablishmentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Establishment>> call = establishmentService.getEstablishments(authHeader);

        final EstablishmentExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)

        call.enqueue(new Callback<List<Establishment>>() {

            @Override
            public void onResponse(Call<List<Establishment>> call, Response<List<Establishment>> response) {//Fonction d'appel à l'api voir les services
                if (response.isSuccessful()) {
                    System.out.println("Establishments Sa fonctionne");
                    List<Establishment> establishments = response.body();
                    self.establishments = establishments;
                }else{
                    System.out.println(response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<List<Establishment>> call, Throwable t) {
                // the network call was a failure
                System.out.println("Establishments Sa fonctionne pas");
                System.out.println(t.toString());
            }
        });

    }
    public void getEstablishment(int id)
    {
        EstablishmentService commentService = retrofit.create(EstablishmentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Establishment> call = commentService.getEstablishment(authHeader,id);

        final EstablishmentExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<Establishment>() {

            @Override
            public void onResponse(Call<Establishment> call, Response<Establishment> response) {
                if (response.isSuccessful()) {
                    System.out.println("Establishment Sa fonctionne");
                    Establishment establishment = response.body();
                    self.establishment = establishment;
                }else{
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Establishment> call, Throwable t) {
                // the network call was a failure
                System.out.println("Establishment Sa fonctionne pas");
                System.out.println(t.toString());
            }

        });

    }
    public void addEstablishment(String name, int location)//Ajoute une entité en base de donnée
    {
        EstablishmentService establishmentService = retrofit.create(EstablishmentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        establishmentService.addEstablishment(authHeader,name,location).enqueue(new Callback<Establishment>() {//Fonction d'appel à l'api voir les services

            @Override
            public void onResponse(Call<Establishment> call, Response<Establishment> response) {
                if(response.isSuccessful()){
                    System.out.println("Establishment envoyer");
                }

            }

            @Override
            public void onFailure(Call<Establishment> call, Throwable t) {
                System.out.println("Establishment Erreur.");
            }
        });

    }
}
