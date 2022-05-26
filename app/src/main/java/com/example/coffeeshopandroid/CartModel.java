package com.example.coffeeshopandroid;

public class CartModel {

    String productName;
    String productDescription;
    String qty;
    String price;
    String productImage;
    String totalPrice;

    public String getTotalPrice() {
        return totalPrice;
    }

    public CartModel(String productName, String productDescription, String qty, String price, String productImage, String totalPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.qty = qty;
        this.price = price;
        this.productImage = productImage;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getProductImage() {
        return productImage;
    }
}
