package com.example.gilian.bars_coop.services;

//Dependencies
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Field;

//Entities
import com.example.gilian.bars_coop.Entity.User;
import com.google.gson.JsonElement;

/**
 * Created by Gilian on 06/08/2017.
 */

public interface UserService
{
    public static final String ENDPOINT_LOCAL = "http://localhost:8888/git/api_EverydayDrinking/web";
    public static final String ENDPOINT_SERVER = "http://192.168.1.254/git/api_EverydayDrinking/web";

    @GET("users")
    Call<List<User>> getUsers(@Header("Authorization")String authHeader);

    @GET("user/{id}")
    Call<User> getUser(@Header("Authorization")String authHeader,@Path("id") int id);

    @POST("user")
    Call<User> addUser(@Field("q") String query);

    @PUT("user")
    Call<User> editUser(@Field("q")String query);




}
