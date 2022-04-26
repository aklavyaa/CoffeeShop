package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ProductList extends AppCompatActivity {
    private RecyclerView recyclerProduct;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerProduct = findViewById(R.id.recycler_product);
        recyclerProduct.setLayoutManager(new LinearLayoutManager(ProductList.this,LinearLayoutManager.VERTICAL,true));
        productAdapter = new ProductAdapter(ProductList.this);
        recyclerProduct.setAdapter(productAdapter);
    }
}