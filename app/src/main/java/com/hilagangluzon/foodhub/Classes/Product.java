package com.hilagangluzon.foodhub.Classes;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Product extends Object
{
    //private String id;
    private String name;
    private String description;
    private double price;
    private int in_stock;
    private String category;
    private int units_sold;

    public static final String COLLECTION_NAME = "products";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_IN_STOCK = "in_stock";

    public static final String[] FIELDS = new String[]
            {
                    FIELD_NAME, FIELD_DESCRIPTION, FIELD_PRICE, FIELD_IN_STOCK
            };

    public Product()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(int in_stock) {
        this.in_stock = in_stock;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    public double pickSubtotal()
    {
        return this.price*this.units_sold;
    }

    @Override
    public String toString()
    {
        return this.name + "\n" + String.format("%.2f", this.price);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getUnits_sold() {
        return units_sold;
    }

    public void setUnits_sold(int units_sold) {
        this.units_sold = units_sold;
    }
}
