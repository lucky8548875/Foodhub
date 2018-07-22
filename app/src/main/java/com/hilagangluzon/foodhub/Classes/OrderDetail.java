package com.hilagangluzon.foodhub.Classes;

import java.util.Date;

import javax.xml.transform.sax.SAXResult;

public class OrderDetail extends Object
{
    private String order_id;
    private Date order_date;
    private String product_id;
    private String name;
    private double price;
    private int quantity;

    public OrderDetail()
    {

    }

    public double pickSubtotal()
    {
        return getPrice()*getQuantity();
    }

    public boolean hasSameProductAs(OrderDetail od)
    {
        return this.product_id.equals(od.getProduct_id());
    }

    /*@Override
    public boolean equals(Object o)
    {
        if(o instanceof OrderDetail)
        {
            return this.product_id.equals(((OrderDetail) o).getProduct_id());
        }
        return false;
    }*/

    @Override
    public String toString()
    {
        return getName() + ": " + String.format("%.2f", getPrice()) + " x " + getQuantity() + " = " + String.format("%.2f", pickSubtotal());
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
