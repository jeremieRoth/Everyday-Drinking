package com.example.gilian.bars_coop.services.Exemples;

import android.util.Base64;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.Comment;
import com.example.gilian.bars_coop.services.CommentService;

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

public class CommentExemple {
    private ListView listView;
    private String login = "jixalas" ;
    private String motDePasse = "password";
    private String base = login + ":" + motDePasse;
    private String authHeader;
    private Retrofit retrofit;

    List<Comment> comments;
    Comment comment;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public CommentExemple()
    {
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
    public void getComments()
    {
        CommentService commentService = retrofit.create(CommentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<List<Comment>> call = commentService.getComments(authHeader);

        final CommentExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)

        call.enqueue(new Callback<List<Comment>>() {

            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    System.out.println("Comments Sa fonctionne");
                    List<Comment> comments = response.body();
                    self.comments = comments;
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
    public void getComment(int id)
    {
        CommentService commentService = retrofit.create(CommentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        Call<Comment> call = commentService.getComment(authHeader,id);

        final CommentExemple self = this;//permet d'acceder aux variable de la classe DrinkExemple dans le callback (this ne fonctionne pas dans le callback)
        call.enqueue(new Callback<Comment>() {

            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    System.out.println("Comments Sa fonctionne");
                    Comment comments = response.body();
                    self.comment = comments;
                }else{
                    System.out.println(response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                // the network call was a failure
                System.out.println("Comments Sa fonctionne pas");
                System.out.println(t.toString());
            }

        });

    }
    public void addComment(String comment, int score,int user, int establishment)//Ajoute une entité en base de donnée
    {
        CommentService commentService = retrofit.create(CommentService.class);
        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        commentService.addComment(authHeader,comment,score,user,establishment).enqueue(new Callback<Comment>() {

            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(response.isSuccessful()){
                    System.out.println("Comment envoyer");
                }

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                System.out.println("Comment Erreur.");
            }
        });

    }
}
