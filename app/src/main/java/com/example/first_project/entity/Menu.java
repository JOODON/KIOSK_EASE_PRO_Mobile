package com.example.first_project.entity;

import java.io.Serializable;

public class Menu implements Serializable {

    private Long id;

    private String name;

    private int price;

    private String category;

    private String StoreName;

    private String description;

    private int amount;

    private String image;

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }
    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public String getImage() {
        return image;
    }
}
