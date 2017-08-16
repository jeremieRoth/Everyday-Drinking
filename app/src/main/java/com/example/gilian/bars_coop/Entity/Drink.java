package com.example.gilian.bars_coop.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gilian on 14/08/2017.
 */

public class Drink {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private float price;
    @SerializedName("establishment")
    @Expose
    private Establishment establishment;

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name) {this.name = name;}

    public float getPrice() {return price;}
    public void setPrice(float price){this.price = price;}

    public Establishment getEstablishment(){return establishment;}
    public void setEstablishment(Establishment establishment){this.establishment = establishment;}
}
