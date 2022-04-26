package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ProxyFileDescriptorCallback;
import android.view.View;
import android.widget.Button;

public class ProductDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        Button placeorder = findViewById(R.id.placeorder);
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDescription.this,PickupOrDelivery.class));
            }
        });
    }
}