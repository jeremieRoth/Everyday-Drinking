package com.example.gilian.bars_coop.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gilian on 06/08/2017.
 */

public class User
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;

    public User() {}

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {this.password = password;}

    public String getUsername() {
        return username;
    }
    public void setUsername(String userName) {this.username = userName;}
}
