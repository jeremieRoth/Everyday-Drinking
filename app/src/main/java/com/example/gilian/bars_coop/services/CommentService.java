package com.example.gilian.bars_coop.services;

import com.example.gilian.bars_coop.Entity.Comment;
import com.example.gilian.bars_coop.Entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by gilian on 14/08/2017.
 */

public interface CommentService {

    public static final String ENDPOINT_LOCAL = "http://localhost:8888/git/api_EverydayDrinking/web";
    public static final String ENDPOINT_SERVER = "http://192.168.1.254/git/api_EverydayDrinking/web";

    @GET("comments")
    Call<List<Comment>> getUsers(@Header("Authorization")String authHeader);

    @GET("comment/{id}")
    Call<Comment> getUser(@Header("Authorization")String authHeader,@Path("id") int id);

    @POST("comment")
    Call<Comment> addUser(@Field("q") String query);

    @PUT("comment")
    Call<Comment> editUser(@Field("q")String query);
}
