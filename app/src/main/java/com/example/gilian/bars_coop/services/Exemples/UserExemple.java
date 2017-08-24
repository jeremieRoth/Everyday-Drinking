package com.example.gilian.bars_coop.services.Exemples;

import android.util.Base64;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.services.UserService;

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

public class UserExemple {
    private ListView listView;
    //identifiant server
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;

    private Retrofit retrofit;

    private List<User> users;
    private User user;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public UserExemple(){
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

    public void addUser(String login, String password, String username)//Ajoute une entité en base de donnée
    {
        UserService userService = retrofit.create(UserService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        userService.addUser(authHeader,login,password,username).enqueue(new Callback<User>() {//Fonction d'appel à l'api voir les services

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    System.out.println("User envoyer");
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("User Erreur.");
            }
        });

    }

    public void getUsers()//Récupère toutes les entités
    {
        UserService locationService = retrofit.create(UserService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<User>> call = locationService.getUsers(authHeader);

        final UserExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {//Fonction d'appel à l'api voir les services
                if (response.isSuccessful()) {
                    System.out.println("User Sa fonctionne");
                    List<User> user = response.body();
                    self.users = user;
                }else{
                    System.out.println(response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // the network call was a failure
                System.out.println("User Sa fonctionne pas");
                System.out.println(t.toString());
            }
        });

    }
    public void getUser(int id)
    {
        UserService commentService = retrofit.create(UserService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<User> call = commentService.getUser(authHeader,id);

        final UserExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    System.out.println("User Sa fonctionne");
                    User user = response.body();
                    self.user = user;
                }else{
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // the network call was a failure
                System.out.println("User Sa fonctionne pas");
                System.out.println(t.toString());
            }

        });

    }

}


