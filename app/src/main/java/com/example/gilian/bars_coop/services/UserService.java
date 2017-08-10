package com.example.gilian.bars_coop.services;

//Dependencies
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Field;

//Entities
import com.example.gilian.bars_coop.Entity.User;

/**
 * Created by Gilian on 06/08/2017.
 */

public interface UserService
{
    public static final String ENDPOINT_LOCAL = "http://localhost:8888/git/api_EverydayDrinking/web";
    public static final String ENDPOINT_SERVER = "http://192.168.1.254/git/api_EverydayDrinking/web";

    @GET("/users")
    List<List<User>> getUsers();

    @GET("/user/{id}")
    List<List<User>> getUser(@Path("id") String id);

    @POST("/user")
    List<List<User>> addUser(@Field("q") String query);

    @PUT("/user")
    List<User> editUser(@Field("q")String query);




}
