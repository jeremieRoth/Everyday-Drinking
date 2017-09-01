package com.example.gilian.bars_coop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.services.UserService;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    //identifiant server
    private String login = "jixalas" ;
    private String motDePasse = "BxeLisE23G";
    private String base = login + ":" + motDePasse;
    private String authHeader;

    private EditText loginA;
    private EditText password;

    private Retrofit retrofit;

    private User user;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

        }else{
            
        }
            loginA = (EditText) findViewById(R.id.login);
            password = (EditText) findViewById(R.id.password);

            //Récupère les droits pour les version récentes d'android.
            int internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {

            } else {
                //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, permission);
            }
/*        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permission);
        }*/

            Button log_up = (Button) findViewById(R.id.btnInscription);

            log_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent);


                }
            });

    }
    public void initRetrofit()//Initialise retrofit obligatoire pour effectuer une requette
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://gilian.ddns.net/git/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        this.retrofit = builder.build();


    }
    public void checkLogin(View log)
    {
        this.initRetrofit();
        this.getUserByLoginAndPassword(this.loginA.getText().toString(),this.password.getText().toString());
    }
    public void getUserByLoginAndPassword(String login, String password)
    {
        UserService userService = retrofit.create(UserService.class);
        authHeader = "Basic " + Base64.encodeToString(this.base.getBytes(), Base64.NO_WRAP);
        Call<User> call = userService.getUserByLoginAndPassword(authHeader,login, password);

        //final UserExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent intent =new Intent(MainActivity.this, MapActivity.class);
                    Bundle extra = new Bundle();
                    User user = response.body();
                    extra.putParcelable("user", user);
                    intent.putExtras(extra);
                    startActivity(intent);


                    //self.user = user;
                }else{
                    Toast.makeText(MainActivity.this, "Utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
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
