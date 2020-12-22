package com.lumadimusalia.su_cafeteria2.bean;

import java.util.ArrayList;

public class Order {


    private String orderCode;
    private ArrayList<FoodItem> items;
    private int totalPrice;
    private  boolean isPaid;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public ArrayList<FoodItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<FoodItem> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        for (int i = 0; i < this.items.size(); i++)
            this.totalPrice += this.items.get(i).getQty() * this.items.get(i).getPrice();
        return this.totalPrice;
    }


    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
