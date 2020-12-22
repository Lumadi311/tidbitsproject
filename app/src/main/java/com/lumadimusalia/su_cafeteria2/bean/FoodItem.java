package com.lumadimusalia.su_cafeteria2.bean;

public class FoodItem {

    private String itemCode;
    private String name;
    private int price;
    private int qty;
    private  String description;
    private int image;

    public FoodItem(String name, int price, String description, int image, String itemCode) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
