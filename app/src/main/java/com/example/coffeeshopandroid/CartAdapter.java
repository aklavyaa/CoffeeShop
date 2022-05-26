package com.example.coffeeshopandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

         TextView textCoffee,price,price_withqty,qty;
         ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textCoffee = view.findViewById(R.id.text_coffee);
            price = view.findViewById(R.id.price);
            imageView = view.findViewById(R.id.image);
            price_withqty = view.findViewById(R.id.price_withqty);
            qty = view.findViewById(R.id.qty);

        }
    }


    private Context context;
    private List<CartModel> list;
    public CartAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_cell, parent, false);

        return new ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

       viewHolder.textCoffee.setText(list.get(position).getProductName());
       viewHolder.price.setText("$"+list.get(position).getPrice());
       viewHolder.qty.setText("Qty: "+list.get(position).getQty());
       viewHolder.price_withqty.setText("$"+list.get(position).getTotalPrice());
        Picasso.get().load(list.get(position).getProductImage()).placeholder(R.drawable.coffee_splash).error(R.drawable.coffee_spill).into(viewHolder.imageView);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}