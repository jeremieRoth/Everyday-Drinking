package com.example.gilian.bars_coop.services.Exemples;

import android.util.Base64;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.Drink;
import com.example.gilian.bars_coop.services.DrinkService;

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

public class DrinkExemple {
    private ListView listView;
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;
    private Retrofit retrofit;

    List<Drink> drinks;
    Drink drink;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public DrinkExemple(){
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
    public void getDrinks()//Récupère toutes les entités
    {
        DrinkService drinkService = retrofit.create(DrinkService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Drink>> call = drinkService.getDrinks(authHeader);

        final DrinkExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)

        call.enqueue(new Callback<List<Drink>>() {

            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {//Fonction d'appel à l'api voir les services
                if (response.isSuccessful()) {
                    System.out.println("Drinks Sa fonctionne");
                    List<Drink> drink = response.body();
                    self.drinks = drink;
                }else{
                    System.out.println(response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<List<Drink>> call, Throwable t) {
                // the network call was a failure
                System.out.println("Drinks Sa fonctionne pas");
                System.out.println(t.toString());
            }
        });

    }
    public void getDrink(int id)
    {
        DrinkService commentService = retrofit.create(DrinkService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Drink> call = commentService.getDrink(authHeader,id);

        final DrinkExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<Drink>() {

            @Override
            public void onResponse(Call<Drink> call, Response<Drink> response) {
                if (response.isSuccessful()) {
                    System.out.println("Drink Sa fonctionne");
                    Drink drink = response.body();
                    self.drink = drink;
                }else{
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Drink> call, Throwable t) {
                // the network call was a failure
                System.out.println("Drink Sa fonctionne pas");
                System.out.println(t.toString());
            }

        });

    }
    public void addDrink(String name, float price, int establishment)//Ajoute une entité en base de donnée
    {
        DrinkService drinkService = retrofit.create(DrinkService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        drinkService.addDrink(authHeader,name,price,establishment).enqueue(new Callback<Drink>() {//Fonction d'appel à l'api voir les services

            @Override
            public void onResponse(Call<Drink> call, Response<Drink> response) {
                if(response.isSuccessful()){
                    System.out.println("Drink envoyer");
                }

            }

            @Override
            public void onFailure(Call<Drink> call, Throwable t) {
                System.out.println("Drink Erreur.");
            }
        });

    }

    public ListView getListView() {
        return listView;
    }

}
