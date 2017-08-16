package com.example.gilian.bars_coop;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.Comment;
import com.example.gilian.bars_coop.Entity.Drink;
import com.example.gilian.bars_coop.Entity.Location;
import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.services.CommentService;
import com.example.gilian.bars_coop.services.DrinkService;
import com.example.gilian.bars_coop.services.EstablishmentService;
import com.example.gilian.bars_coop.services.LocationService;
import com.example.gilian.bars_coop.services.MapActivity;
import com.example.gilian.bars_coop.services.UserService;
import com.google.gson.JsonElement;

import java.util.List;


import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.packageNames;
import static android.R.attr.permission;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;
    private Retrofit retrofit;


    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Récupère les droits pour les version récentes d'android.
        int internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, permission);
        }

        initRetrofit();//Initialise retrofit pour accéder à l'API
        getUsers();//Récupère les utilisateurs sur l'api
        getEstablishment();
        getDrink();
        getComment();
        getLocation();
        Button loginButton = (Button) findViewById(R.id.btnlogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, MapActivity.class);
                //Bundle extra = new Bundle();
                User user =new User();
                user.setId(1);
                user.setLogin("111");
                user.setPassword("azerty");
                user.setUsername("vador");
                //extra.putString("user", "vador");
                intent.putExtra("user", user);
                startActivity(intent);


            }
        });


    }
    public void initRetrofit()//Initialise rétrofit
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://gilian.ddns.net/git/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();


    }
    public void getUsers()//Récupére l'entité user
    {
        UserService userService = retrofit.create(UserService.class);// Récupere le service pour acceder a l'entité user
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);//code le mot de passe
        Call<List<User>> call = userService.getUsers(authHeader);// insére les identifiants dans le header pour le serveur

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {//Si retrofit a bien récupérer les données
                if (response.isSuccessful()) {
                    System.out.println("Users Sa fonctionne");
                    System.out.println(response.code());
                    System.out.println(response.body().toString());
                    List<User> users = response.body();
                    System.out.println(users);
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(users.get(i).getId());
                    }
                }else{
                    System.out.println(response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {//Si une erreur avec retrofit est survenue
                // the network call was a failure
                System.out.println("Users Sa fonctionne pas");
                System.out.println(t.toString());
            }
        });

    }
    public void getEstablishment()
    {
        EstablishmentService establishmentService = retrofit.create(EstablishmentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Establishment>> call = establishmentService.getUsers(authHeader);

        call.enqueue(new Callback<List<Establishment>>() {

            @Override
            public void onResponse(Call<List<Establishment>> call, Response<List<Establishment>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Establishments Sa fonctionne");
                    System.out.println(response.code());
                    System.out.println(response.body().toString());
                    List<Establishment> establishments = response.body();
                    System.out.println(establishments);
                    for (int i = 0; i < establishments.size(); i++) {
                        System.out.println(establishments.get(i).getLocation());
                    }
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
    public void getDrink()
    {
        DrinkService drinkService = retrofit.create(DrinkService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Drink>> call = drinkService.getUsers(authHeader);

        call.enqueue(new Callback<List<Drink>>() {

            @Override
            public void onResponse(Call<List<Drink>> call, Response<List<Drink>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Drinks Sa fonctionne");
                    System.out.println(response.code());
                    System.out.println(response.body().toString());
                    List<Drink> drink = response.body();
                    System.out.println(drink);
                    for (int i = 0; i < drink.size(); i++) {
                        System.out.println(drink.get(i).getName());
                    }
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
    public void getComment()
    {
        CommentService commentService = retrofit.create(CommentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Comment>> call = commentService.getUsers(authHeader);

        call.enqueue(new Callback<List<Comment>>() {

            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Comments Sa fonctionne");
                    System.out.println(response.code());
                    System.out.println(response.body().toString());
                    List<Comment> comments = response.body();
                    System.out.println(comments);
                    for (int i = 0; i < comments.size(); i++) {
                        System.out.println(comments.get(i).getComment());
                    }
                }else{
                    System.out.println(response.errorBody());
                }

            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                // the network call was a failure
                System.out.println("Comments Sa fonctionne pas");
                System.out.println(t.toString());
            }
        });

    }
    public void getLocation()
    {
        LocationService locationService = retrofit.create(LocationService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Location>> call = locationService.getUsers(authHeader);

        call.enqueue(new Callback<List<Location>>() {

            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Locations Sa fonctionne");
                    System.out.println(response.code());
                    System.out.println(response.body().toString());
                    List<Location> locations = response.body();
                    System.out.println(locations);
                    for (int i = 0; i < locations.size(); i++) {
                        System.out.println(locations.get(i).getLatitude());
                    }
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
}
