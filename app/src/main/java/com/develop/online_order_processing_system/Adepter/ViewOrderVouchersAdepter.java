package com.develop.online_order_processing_system.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.online_order_processing_system.ModelClasses.OrderStatus;
import com.develop.online_order_processing_system.R;

import java.util.ArrayList;

public class ViewOrderVouchersAdepter extends RecyclerView.Adapter<ViewOrderVouchersAdepter.MyViewHolder> {

    private Context context;
    private ArrayList<OrderStatus> orderStatusArrayList;


    public ViewOrderVouchersAdepter(Context context, ArrayList<OrderStatus> orderStatusArrayList) {
        this.context = context;
        this.orderStatusArrayList = orderStatusArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewOrderVouchersAdepter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_vaucher, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        OrderStatus orderStatus = orderStatusArrayList.get(position);

        holder.vaucherOrderName.setText(orderStatus.getProductName());

        String price = String.valueOf(orderStatus.getPrice());
        String quantity = String.valueOf(orderStatus.getQuantity());
        String total = String.valueOf(orderStatus.getPrice()*orderStatus.getQuantity());

        holder.vaucherOrderPrice.setText(price+"৳");
        holder.vaucherOrderQuantity.setText(quantity);
        holder.vaucherTotal.setText(total+"৳");
    }

    @Override
    public int getItemCount() {
        return orderStatusArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView vaucherOrderName,vaucherOrderPrice,vaucherOrderQuantity,vaucherTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vaucherOrderName = itemView.findViewById(R.id.productNameVaucher);
            vaucherOrderPrice = itemView.findViewById(R.id.productPriceVaucher);
            vaucherOrderQuantity = itemView.findViewById(R.id.productQuantityVaucher);
            vaucherTotal = itemView.findViewById(R.id.productTotalVaucher);
        }
    }

}
