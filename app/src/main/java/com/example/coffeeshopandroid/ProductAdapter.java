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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

         TextView textCoffee,description,price;
         ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textCoffee = view.findViewById(R.id.text_coffee);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            imageView = view.findViewById(R.id.image);
        }
    }


    private Context context;
    private List<ProductModel> list;
    public ProductAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_recycler_cell, parent, false);

        return new ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

       viewHolder.textCoffee.setText(list.get(position).getProductName());
       viewHolder.description.setText(list.get(position).getProductDescription());
       viewHolder.price.setText("$"+list.get(position).getPrice());

        Picasso.get().load(list.get(position).getProductImage()).placeholder(R.drawable.coffee_splash).error(R.drawable.coffee_spill).into(viewHolder.imageView
        );
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomConstants.productId = list.get(position).getProductId();
                context.startActivity(new Intent(context,ProductDescription.class));
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}