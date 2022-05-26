package com.example.coffeeshopandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DashAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO  = 1;
    private static final int LAYOUT_THREE = 2;
    private LayoutInflater mInflater;
    private Context context;
    private List<CategoryModel> catList;
    private List<ProductModel> featuredList;


    DashAdap(Context context, List<CategoryModel> catList, List<ProductModel> featuredList){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.catList = catList;
        this.featuredList = featuredList;

    }

    class ViewHolder0 extends RecyclerView.ViewHolder {

        RecyclerView offerRecycler;
        TextView logout;
        TextView name;

        public ViewHolder0(View itemView){
            super(itemView);


            name = itemView.findViewById(R.id.name);
            offerRecycler = itemView.findViewById(R.id.recycler_offers);
            logout = itemView.findViewById(R.id.logout);

        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        RecyclerView catRecycler;

        public ViewHolder1(View itemView){
            super(itemView);
            catRecycler = itemView.findViewById(R.id.recycler_catefgories);

        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        RecyclerView featureRecycler;

        public ViewHolder2(View itemView) {
            super(itemView);
            featureRecycler = itemView.findViewById(R.id.recycler_feature);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType==LAYOUT_ONE)
        {
            view = mInflater.inflate(R.layout.top_view_dashboard,parent,false);
            viewHolder = new ViewHolder0(view);
        }
        else if (viewType == LAYOUT_TWO)
        {
            view = mInflater.inflate(R.layout.middle_view_dashboard,parent,false);
            viewHolder= new ViewHolder1(view);
        } else {
            view = mInflater.inflate(R.layout.end_view_dashboard,parent,false);
            viewHolder= new ViewHolder2(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//
                viewHolder0.name.setText("Yuvraj");
                viewHolder0.logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        ((Activity)context).finish();
                    }
                });
                viewHolder0.offerRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                viewHolder0.offerRecycler.setAdapter(new CustomAdapter());


                break;

            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1)holder;
                viewHolder1.catRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
                viewHolder1.catRecycler.setAdapter(new CatAdapter(context,catList));

                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                viewHolder2.featureRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
                viewHolder2.featureRecycler.setAdapter(new FeatureAapter(featuredList));
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {
         if(position == 0) return LAYOUT_ONE; else if (position == 1) return LAYOUT_TWO; else  return LAYOUT_THREE;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
