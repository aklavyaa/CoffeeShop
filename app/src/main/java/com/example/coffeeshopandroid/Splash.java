package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    private String TAG = Splash.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {




                if (currentUser!=null) {
                    if (currentUser.getUid().equalsIgnoreCase(CustomConstants.uuid)) {
                        Intent intent = new Intent(Splash.this, Dashboard.class);
                        startActivity(intent);
                        finish();
//
                    } else {

                        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                        Splash.this.startActivity(mainIntent);
                        Splash.this.finish();
                    }
                } else {

                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }



//                if(currentUser != null){
//
//                } else {
//                    Intent mainIntent = new Intent(Splash.this,MainActivity.class);
//                    Splash.this.startActivity(mainIntent);
//                    Splash.this.finish();
//                }
                /* Create an Intent that will start the Menu-Activity. */

            }
        }, 2000);

    }
}