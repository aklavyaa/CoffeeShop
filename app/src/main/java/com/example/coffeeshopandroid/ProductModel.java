package com.example.coffeeshopandroid;

public class ProductModel {
    private String cat_id;
    private String isFeatured;
    private String productDescription;
    private String productId;
    private String productImage;
    private String productName;
    private String price;

    public ProductModel(String cat_id, String isFeatured, String productDescription, String productId, String productImage, String productName,String price) {
        this.cat_id = cat_id;
        this.isFeatured = isFeatured;
        this.productDescription = productDescription;
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getIsFeatured() {
        return isFeatured;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }
}
