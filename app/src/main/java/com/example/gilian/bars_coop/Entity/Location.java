package com.example.gilian.bars_coop.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilian on 14/08/2017.
 */

public class Location {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("longitutde")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;

    public int getId() {return id;}
    public void setId(int id){this.id = id;}

    public String getLongitude(){return longitude;}
    public void setLongitude(String longitude){this.longitude = longitude;}

    public String getLatitude(){return latitude;}
    public void setLatitude(String latitude){this.latitude = latitude;}
}
