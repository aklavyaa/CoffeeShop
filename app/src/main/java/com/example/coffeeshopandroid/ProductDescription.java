package com.example.coffeeshopandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Bundle;
import android.os.ProxyFileDescriptorCallback;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDescription extends AppCompatActivity {

    private String TAG = ProductDescription.class.getSimpleName();
    private ImageView coffeeCup;
    private FirebaseFirestore db;
    private List<ProductModel> list  = new ArrayList<>();
    private TextView productName,productDescription,productPrice,plus,minus,qty;
    private int intQty = 1;
    private List<CartModel> cartList = new ArrayList<>();

    private String pricesToBeSet = "";

    private String getPriceCustomised(int size){
        float fPrice =  Float.parseFloat(price);
        float totalPrice = fPrice * size;
        productPrice.setText("$"+String.valueOf(totalPrice));

        return String.valueOf(totalPrice);

    }


    private void placeOrder(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();



        float fValue = Float.parseFloat(pricesToBeSet);
        Float totalValue = fValue * (float)intQty;


        Map<String, String> cartObj = new HashMap<>();
        cartObj.put("product_name", list.get(0).getProductName());
        cartObj.put("product_description",list.get(0).getProductDescription() );
        cartObj.put("qty", String.valueOf(intQty));
        cartObj.put("total_price", String.valueOf(totalValue));
        cartObj.put("image", list.get(0).getProductImage());
        cartObj.put("price", pricesToBeSet);
        cartObj.put("uuid",user.getUid());


        db.collection("cart_list").document()
                .set(cartObj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "DocumentSnapshot successfully written!");
                        startActivity(new Intent(ProductDescription.this,CartList.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error writing document", e);
                    }
                });



    }



    private void setQty(boolean isIncrease){

        if (isIncrease){
            intQty++;
        }else {
            if (intQty == 1){
                return;
            }
            intQty--;
        }
        qty.setText(String.valueOf(intQty));

    }

    RadioGroup rad_grp;
    String price;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        Button placeorder = findViewById(R.id.placeorder);
         rad_grp = findViewById(R.id.rad_grp);



         RadioButton small_radio = findViewById(R.id.small_radio);
         RadioButton medium_radio = findViewById(R.id.medium_radio);
         RadioButton large_radio = findViewById(R.id.large_radio);
         RadioButton xtralarge_radio = findViewById(R.id.xtralarge_radio);
        small_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                  pricesToBeSet =  getPriceCustomised(1);

                }
            }
        });
        medium_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pricesToBeSet =  getPriceCustomised(2);
                }
            }
        });
        large_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pricesToBeSet =  getPriceCustomised(3);

                }
            }
        });
        xtralarge_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pricesToBeSet =  getPriceCustomised(4);

                }
            }
        });


//         small_radio
//                medium_radio
//
//        large_radio
//                xtralarge_radio
        coffeeCup = findViewById(R.id.coffee_cup);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        qty = findViewById(R.id.qty);



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQty(true);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQty(false);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);

        db = FirebaseFirestore.getInstance();
        db.collection("Products").whereEqualTo("product_id",CustomConstants.productId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    list.add(new ProductModel(
                            document.getData().get("cat_id").toString()
                            , document.getData().get("isFeatured").toString()
                            , document.getData().get("product_description").toString()
                            , document.getData().get("product_id").toString()
                            , document.getData().get("product_image").toString()
                            , document.getData().get("product_name").toString()
                            , document.getData().get("price").toString()
                    ));
                }

                productName.setText(list.get(0).getProductName());
                productDescription.setText(list.get(0).getProductDescription());
                productPrice.setText("$"+list.get(0).getPrice());
                Picasso.get().load(list.get(0).getProductImage()).into(coffeeCup);
                price = list.get(0).getPrice();

            }

        });
        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();

            }
        });
    }
}