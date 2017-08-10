package com.example.gilian.bars_coop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.gilian.bars_coop.Entity.User;
import com.example.gilian.bars_coop.services.UserService;

import java.util.List;


import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;


    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://localhost:8888/git/api_EverydayDrinking/web/").addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        UserService user = retrofit.create(UserService.class);

        Call<List<User>> call = user.getUsers();

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                System.out.println("Sa fonctionne");
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
            }
        });
    }
}
