package com.example.gilian.bars_coop.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gilian on 06/08/2017.
 */

public class User implements Parcelable
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
    //Parcelling
    public User(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.login = data[1];
        this.password = data[2];
        this.username = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{
                Integer.toString(this.id),
                this.login,
                this.password,
                this.username});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public User createFromParcel(Parcel in){
            return new User(in);
        }

        public User[] newArray(int size){
            return new User[size];
        }
    };
}
