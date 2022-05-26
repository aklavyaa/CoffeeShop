package com.example.coffeeshopandroid;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FeatureAapter extends RecyclerView.Adapter<FeatureAapter.ViewHolder> {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCoffee, description,price;
        ImageView prodImage;

        public ViewHolder(View view) {
            super(view);
          textCoffee =    view.findViewById(R.id.text_coffee);
          description =  view.findViewById(R.id.description);
          price = view.findViewById(R.id.price);
          prodImage = view.findViewById(R.id.image);





            // Define click listener for the ViewHolder's View

        }


    }

    private List<ProductModel> featuredList;

    public FeatureAapter(List<ProductModel> featuredList) {
        this.featuredList = featuredList;
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

            viewHolder.textCoffee.setText(featuredList.get(position).getProductName());
            viewHolder.description.setText(featuredList.get(position).getProductDescription());
            viewHolder.price.setText("$"+featuredList.get(position).getPrice());
        Picasso.get()
                .load(featuredList.get(position).getProductImage())
                .placeholder(R.drawable.coffee_splash)
                .error(R.drawable.coffee_spill)
                .into(viewHolder.prodImage);

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return featuredList.size();
    }
}