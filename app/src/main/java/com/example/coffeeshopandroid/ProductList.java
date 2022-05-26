package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {
    private RecyclerView recyclerProduct;
    private ProductAdapter productAdapter;
    private FirebaseFirestore db;
    private String TAG = ProductList.class.getSimpleName();
    private List<ProductModel> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerProduct = findViewById(R.id.recycler_product);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerProduct.setLayoutManager(new LinearLayoutManager(ProductList.this,LinearLayoutManager.VERTICAL,true));
        db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("cat_id",CustomConstants.catId)
                .get().addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               for (QueryDocumentSnapshot document : task.getResult()) {
                   Log.e(TAG, "insideforloop " );
                   list.add(new ProductModel(
                           document.getData().get("cat_id").toString()
                           ,document.getData().get("isFeatured").toString()
                           ,document.getData().get("product_description").toString()
                           ,document.getData().get("product_id").toString()
                           ,document.getData().get("product_image").toString()
                           ,document.getData().get("product_name").toString()
                           ,document.getData().get("price").toString()));
               }

               productAdapter = new ProductAdapter(ProductList.this,list);
               recyclerProduct.setAdapter(productAdapter);


           }
        });




    }
}