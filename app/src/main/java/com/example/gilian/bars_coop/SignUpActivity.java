package com.example.gilian.bars_coop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.services.UserService;

import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gilian on 24/08/2017.
 */

public class SignUpActivity extends AppCompatActivity {

    private ListView listView;
    //identifiant server
    private String login = "jixalas" ;
    private String motDePasse = "BxeLisE23G";
    private String base = login + ":" + motDePasse;
    private String authHeader;

    private EditText loginA;
    private EditText name;
    private EditText firstname;
    private EditText password;
    private EditText verifpassword;

    private Pattern patternName = Pattern.compile("^[a-zA-Z0-9]");
    private Pattern patternPwd = Pattern.compile("^[a-zA-Z0-9]");
    private Pattern patternFN = Pattern.compile("^[a-zA-Z0-9]");
    private Pattern patternPwdVerif = Pattern.compile("^[a-zA-Z0-9]");



    private Retrofit retrofit;

    private User user;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button log_up = (Button) findViewById(R.id.btnInscription);

        loginA =(EditText) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.name);
        firstname = (EditText) findViewById(R.id.firstname);
        password = (EditText) findViewById(R.id.password);
        verifpassword = (EditText) findViewById(R.id.verifpassword);
    }

    public void initRetrofit()//Initialise retrofit obligatoire pour effectuer une requette
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://gilian.ddns.net/git/api_EverydayDrinking/web/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();


    }

    public void inscription(View log)
    {
        System.out.print("Inscription");
        if ( loginA.getText() == null ) {
            Toast.makeText(SignUpActivity.this, "Le champ LOGIN n'est pas rempli", Toast.LENGTH_SHORT).show();
        }
        else {
            if (name.getText() == null ) {//|| this.patternName.matcher(this.loginA.toString()).find()
                Toast.makeText(SignUpActivity.this, "Le champ Name n'est pas rempli ou et Incorrect" + name.getText(), Toast.LENGTH_SHORT).show();
            } else {
                if (firstname.getText() == null ) {//|| this.patternPwd.matcher(this.firstname.getText().toString()).find()
                    Toast.makeText(SignUpActivity.this, "Le champ FirstName n'est pas rempli ou et Incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.getText() == null ) {//|| this.patternPwdVerif.matcher(this.verifpassword.toString()).find()
                        Toast.makeText(SignUpActivity.this, "Le champ Password ou VérifPassword n'est pas rempli", Toast.LENGTH_SHORT).show();
                    } else {
                        this.initRetrofit();
                        this.addUser(this.loginA.getText().toString(),this.password.getText().toString(), this.name.getText().toString());

                    }
                }

            }
        }
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
                    Intent intent =new Intent(SignUpActivity.this, MainActivity.class);
                    Bundle extra = new Bundle();
                    startActivity(intent);

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("User Erreur.");
            }
        });

    }



}
