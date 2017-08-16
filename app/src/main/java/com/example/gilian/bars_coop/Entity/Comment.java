package com.example.gilian.bars_coop.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilian on 14/08/2017.
 */

public class Comment {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("establishment")
    @Expose
    private Establishment establishment;

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    public String getComment(){return comment;}
    public void setComment(String comment){this.comment = comment;}

    public String getScore(){return score;}
    public void setScore(String score){this.score = score;}

    public Establishment getEstablishment(){return establishment;}
    public void setEstablishment(Establishment establishment){this.establishment = establishment;}
}
