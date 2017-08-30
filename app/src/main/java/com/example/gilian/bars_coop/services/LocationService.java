package com.example.gilian.bars_coop.services;

import com.example.gilian.bars_coop.Entity.Location;

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

public interface LocationService {

    public static final String ENDPOINT_LOCAL = "http://localhost:8888/git/api_EverydayDrinking/web";
    public static final String ENDPOINT_SERVER = "http://192.168.1.254/git/api_EverydayDrinking/web";

    @GET("locations")
    Call<List<Location>> getLocations(@Header("Authorization")String authHeader);

    @GET("location/{id}")
    Call<Location> getLocation(@Header("Authorization")String authHeader,@Path("id") int id);

    @FormUrlEncoded
    @POST("location")
    Call<Location> addLocation(@Header("Authorization")String authHeader,
                           @Field("longitude") float longitude,
                           @Field("latitude")float latitude);
    @FormUrlEncoded
    @PUT("location/{id}")
    Call<Location> editLocation(@Header("Authorization")String authHeader,
                            @Field("longitude") float login,
                            @Field("latitude") float password);
}
