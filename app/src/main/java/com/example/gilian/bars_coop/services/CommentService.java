package com.example.gilian.bars_coop.services;

import com.example.gilian.bars_coop.Entity.Comment;

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

public interface CommentService {

    public static final String ENDPOINT_LOCAL = "http://localhost:8888/git/api_EverydayDrinking/web";
    public static final String ENDPOINT_SERVER = "http://192.168.1.254/git/api_EverydayDrinking/web";

    @GET("comments")
    Call<List<Comment>> getComments(@Header("Authorization")String authHeader);

    @GET("comment/{id}")
    Call<Comment> getComment(@Header("Authorization")String authHeader,@Path("id") int id);

    @FormUrlEncoded
    @POST("comment")
    Call<Comment> addComment(@Header("Authorization")String authHeader,
                             @Field("comment")String comment,
                             @Field("score")int score,
                             @Field("user")int user,
                             @Field("establishment")int establishment);

    @FormUrlEncoded
    @PUT("comment")
    Call<Comment> editComment(@Header("Authorization")String authHeader,
                              @Field("comment")String comment,
                              @Field("score")int score,
                              @Field("user")int user,
                              @Field("establishment")int establishment);
}
