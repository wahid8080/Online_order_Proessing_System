package com.nishu.inventory_management_and_order_processing_system.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nishu.inventory_management_and_order_processing_system.ModelClasses.Feedback;
import com.nishu.inventory_management_and_order_processing_system.R;

import java.util.ArrayList;

public class ReviewAdepter extends RecyclerView.Adapter<ReviewAdepter.MyViewHolder> {

    public ReviewAdepter() {

    }

    private ArrayList<Feedback> feedbackArrayList;
    private Context context;
    private String shopName;

    public ReviewAdepter(ArrayList<Feedback> feedbackArrayList, Context context,String shopName) {
        this.feedbackArrayList = feedbackArrayList;
        this.context = context;
        this.shopName = shopName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewAdepter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comment_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Feedback feedback = feedbackArrayList.get(position);

        holder.shopName.setText("Rating : "+String.valueOf(feedback.getRating()));
        holder.comment.setText("Feedback : " +feedback.getComment());

    }

    @Override
    public int getItemCount() {
        return feedbackArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shopName,comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            shopName = itemView.findViewById(R.id.shopkeeperNameForComment);
            comment = itemView.findViewById(R.id.shopKeeperComment);
        }
    }
}
