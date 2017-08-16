package com.example.gilian.bars_coop.services;

import com.example.gilian.bars_coop.Entity.Drink;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by gilian on 14/08/2017.
 */

public interface DrinkService {

    public static final String ENDPOINT_LOCAL = "http://localhost:8888/git/api_EverydayDrinking/web";
    public static final String ENDPOINT_SERVER = "http://192.168.1.254/git/api_EverydayDrinking/web";

    @GET("drinks")
    Call<List<Drink>> getDrinks(@Header("Authorization")String authHeader);

    @GET("drink/{id}")
    Call<Drink> getDrink(@Header("Authorization")String authHeader,@Path("id") int id);

    @FormUrlEncoded
    @POST("drink")
    Call<Drink> addDrink(@Header("Authorization")String authHeader,
                        @Field("name") String name,
                        @Field("price")float price,
                        @Field("establishment")int establishment);

    @FormUrlEncoded
    @PUT("drink")
    Call<Drink> editDrink(@Header("Authorization")String authHeader,
                         @Field("name") String name,
                         @Field("price")float price,
                         @Field("establishment")int establishment);
}
