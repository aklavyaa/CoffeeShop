package com.example.coffeeshopandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CartList extends AppCompatActivity {
    private RecyclerView recyclerCart;
    private FirebaseFirestore db;
    private List<CartModel> cartList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private Button checkoutbtn;
    private String TAG = CartList.class.getSimpleName();
    private Float totalPrice= Float.valueOf(0);
    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        recyclerCart = findViewById(R.id.recycler_cart);
        checkoutbtn = findViewById(R.id.checkoutbtn);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                order id (Random)
//                list
//                total price


//                int orderid = gen();
//                cartList
//                  totalPrice


                Map<String, Object> docData = new HashMap<>();
                docData.put("order_id", "Hello world!");
                docData.put("total_price", String.valueOf(totalPrice));
                docData.put("cart_list",cartList);

                db.collection("order_list").document().set(docData).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Log.e(TAG, "Successful");
                       startActivity(new Intent(CartList.this,PickupOrDelivery.class));

                   }
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failure");
                });

            }
        });

        recyclerCart.setLayoutManager(new LinearLayoutManager(CartList.this,LinearLayoutManager.VERTICAL,false));


        db = FirebaseFirestore.getInstance();
        db.collection("cart_list").whereEqualTo("uuid",user.getUid()).get().addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               for (QueryDocumentSnapshot document : task.getResult()) {
                   cartList.add(new CartModel(document.getString("product_name"),document.getString("product_description"),document.getString("qty"),document.getString("price"),document.getString("image"),document.getString("total_price")));
               }
               cartAdapter = new CartAdapter(CartList.this,cartList);
               recyclerCart.setAdapter(cartAdapter);

               for (CartModel cartObj:
                    cartList) {
                 totalPrice =  totalPrice + Float.parseFloat(cartObj.getTotalPrice());
               }
               checkoutbtn.setText("Check Out (Total: $"+totalPrice+")");
           }
        });
    }
}