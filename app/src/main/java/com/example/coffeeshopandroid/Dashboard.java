package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.proto.ProtoOutputStream;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private RecyclerView recyclerDashboard;
    private LinearLayoutManager layoutManager;
    private FirebaseFirestore db;
    private CollectionReference cr;
    private List<CategoryModel> catList = new ArrayList<>();
    private List<ProductModel> featuredList = new ArrayList<>();
    private String TAG = Dashboard.class.getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        db = FirebaseFirestore.getInstance();
        recyclerDashboard = findViewById(R.id.recyler_dashboard);
        layoutManager = new LinearLayoutManager(Dashboard.this);
        recyclerDashboard.setLayoutManager(layoutManager);

        cr = db.collection("Menu");
        cr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.e(TAG, "insidescuceessful" );
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.e(TAG, "insideforloop " );
                    catList.add(new CategoryModel(document.getData().get("id").toString(),document.getData().get("Category").toString()));
                }


                db.collection("Products").whereEqualTo("isFeatured","0").get().addOnCompleteListener(task1 -> {
                    Log.e(TAG, "insidescuceessful" );
                    for (QueryDocumentSnapshot document : task1.getResult()) {
                        Log.e(TAG, "insideforloop " );
                        featuredList.add(new ProductModel(
                                document.getData().get("cat_id").toString()
                                ,document.getData().get("isFeatured").toString()
                                ,document.getData().get("product_description").toString()
                                ,document.getData().get("product_id").toString()
                                ,document.getData().get("product_image").toString()
                                ,document.getData().get("product_name").toString()
                                ,document.getData().get("price").toString()
                        ));
                    }

                    DashAdap adapter = new DashAdap(Dashboard.this,catList,featuredList);
                    recyclerDashboard.setAdapter(adapter);
                });

            }
        });

    }
}