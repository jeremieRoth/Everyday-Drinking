package com.example.gilian.bars_coop.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilian on 30/08/2017.
 */

public class Event {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("establishment")
    @Expose
    private Establishment establishment;

    public int getId() {return id;}
    public void setId(int id){this.id = id;}

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public Establishment getEstablishment(){return this.establishment;}
    public void setEstablishment(Establishment establishment){this.establishment = establishment;}
}
