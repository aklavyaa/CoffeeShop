package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Dashboard extends AppCompatActivity {

    private RecyclerView recyclerDashboard;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerDashboard = findViewById(R.id.recyler_dashboard);
        layoutManager = new LinearLayoutManager(Dashboard.this);
        recyclerDashboard.setLayoutManager(layoutManager);
        DashAdap adapter = new DashAdap(Dashboard.this);
        recyclerDashboard.setAdapter(adapter);


    }
}