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

import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.Entity.Establishment;
import com.example.gilian.bars_coop.services.CommentService;
import com.example.gilian.bars_coop.services.DrinkService;
import com.example.gilian.bars_coop.services.EstablishmentService;
import com.example.gilian.bars_coop.services.LocationService;
import com.example.gilian.bars_coop.services.UserService;
import com.example.gilian.bars_coop.services.MapActivity;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.permission;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    //identifiant server
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;

    private Retrofit retrofit;

    private User user;

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
        this.getUser(1);
        Button loginButton = (Button) findViewById(R.id.btnlogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this, MapActivity.class);
                Bundle extra = new Bundle();
                extra.putString("user", "vador");
                intent.putExtras(extra);
                startActivity(intent);


            }
        });


    }
    public void initRetrofit()//Initialise retrofit obligatoire pour effectuer une requette
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.254/git/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();


    }
    public void getUser(int id)
    {
        UserService userService = retrofit.create(UserService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<User> call = userService.getUser(authHeader,id);

        final MainActivity self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    System.out.println("User Sa fonctionne");
                    User user = response.body();
                    self.user = user;
                    System.out.println(user.getId());
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
