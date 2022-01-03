package com.tw.ShoppingCart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private String name;
    private float price;

    public Product(){

    }

    public Product(String name, float price) {
        this.name=name;
        this.price=price;
    }


    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name){
        this.name =name;
    }
    public void setPrice(float price){
        this.price = price;
    }

    public int getId() {
        return id;
    }
}

