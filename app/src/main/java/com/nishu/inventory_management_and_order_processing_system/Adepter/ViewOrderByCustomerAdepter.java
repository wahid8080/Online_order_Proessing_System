package com.nishu.inventory_management_and_order_processing_system.Adepter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nishu.inventory_management_and_order_processing_system.FeedBack;
import com.nishu.inventory_management_and_order_processing_system.Message;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.OrderStatusForCompany;
import com.nishu.inventory_management_and_order_processing_system.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewOrderByCustomerAdepter extends RecyclerView.Adapter<ViewOrderByCustomerAdepter.MyViewHolder> {

    private Context context;
    private ArrayList<OrderStatusForCompany> orderStatusForCompanyArrayList;
    private String key;

    public ViewOrderByCustomerAdepter(Context context, ArrayList<OrderStatusForCompany> orderStatusForCompanyArrayList, String key) {
        this.context = context;
        this.orderStatusForCompanyArrayList = orderStatusForCompanyArrayList;
        this.key = key;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewOrderByCustomerAdepter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_by_custoner_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final OrderStatusForCompany orderStatusForCompany = orderStatusForCompanyArrayList.get(position);

        holder.orderName.setText(orderStatusForCompany.getProductName());
        holder.orderPrice.setText(orderStatusForCompany.getPrice() + "৳");
        holder.orderQuantity.setText(orderStatusForCompany.getQuantity());
        holder.orderTotal.setText(orderStatusForCompany.getTotal() + "৳");
        holder.customerInfo.setText(orderStatusForCompany.getCustomerInfo());
        holder.date.setText(orderStatusForCompany.getDate());
        holder.companysName.setText(orderStatusForCompany.getCompanysTitle());

        holder.feedback.setText(Html.fromHtml("<u> Feedback </u>"));

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Message.class);
                intent.putExtra("companysId",orderStatusForCompany.getCompanysId());
                intent.putExtra("customerId",orderStatusForCompany.getCustomerId());
                context.startActivity(intent);
            }
        });

        if (key.equals("shopkeeper")) {


            holder.feedback.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.GONE);
            holder.orderAccepted.setVisibility(View.VISIBLE);
            holder.orderAccepted.setText("Pending...");
            holder.linearLayoutForDelivery.setVisibility(View.GONE);

            holder.pendding.setVisibility(View.VISIBLE);

            if (orderStatusForCompany.getOrder().equals("Order Accepted"))
            {
                holder.orderAccept.setVisibility(View.VISIBLE);
                holder.pendding.setVisibility(View.GONE);
            }
            if (orderStatusForCompany.getPacking().equals("packing..."))
            {
                holder.orderAccept.setVisibility(View.GONE);
                holder.packingForImae.setVisibility(View.VISIBLE);
                holder.orderAccepted.setText("packing...");


            }
            if (orderStatusForCompany.getDelivery_trak().equals("Delivering Now"))
            {
                holder.orderAccept.setVisibility(View.GONE);
                holder.delivering.setVisibility(View.VISIBLE);
                holder.packingForImae.setVisibility(View.GONE);
                holder.orderAccepted.setText("Delivering Now");
            }
            if (orderStatusForCompany.getDeliveryComplete().equals("delivery Complete"))
            {
                holder.orderAccept.setVisibility(View.GONE);
                holder.deliveryCompleteImg.setVisibility(View.VISIBLE);
                holder.delivering.setVisibility(View.GONE);

                holder.orderAccepted.setText("delivery Complete");

            }


            holder.feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, FeedBack.class);
                    intent.putExtra("companysTitle",orderStatusForCompany.getCompanysTitle());
                    intent.putExtra("customerId",orderStatusForCompany.getCustomerId());
                    intent.putExtra("companysId",orderStatusForCompany.getCompanysId());
                    context.startActivity(intent);


                }
            });
        }
        if (key.equals("company")) {


            holder.feedback.setVisibility(View.GONE);
            if (orderStatusForCompany.getPacking().equals("packing..."))
            {holder.packing.setVisibility(View.GONE);}
            if (orderStatusForCompany.getDelivery_trak().equals("Delivering Now"))
            {holder.delivering.setVisibility(View.GONE);}
            if (orderStatusForCompany.getDeliveryComplete().equals("delivery Complete"))
            {holder.deliveryComplete.setVisibility(View.GONE);}


            holder.linearLayoutForDelivery.setVisibility(View.GONE);
            if (orderStatusForCompany.getOrder().equals("Order Accepted"))
            {
                holder.checkBox.setVisibility(View.GONE);
                holder.orderAccepted.setText(orderStatusForCompany.getOrder());
                holder.orderAccepted.setVisibility(View.VISIBLE);
                holder.linearLayoutForDelivery.setVisibility(View.GONE);

                if (orderStatusForCompany.getOrder().equals("Order Accepted"))
                {
                    holder.pendding.setVisibility(View.GONE);
                    holder.orderAccept.setVisibility(View.VISIBLE);
                }
                if (orderStatusForCompany.getPacking().equals("packing..."))
                {
                    holder.orderAccepted.setText("packing...");
                    holder.packingForImae.setVisibility(View.VISIBLE);
                }
                if (orderStatusForCompany.getDelivery_trak().equals("Delivering Now"))
                {
                    holder.orderAccepted.setText("Delivering Now");
                    holder.delivering.setVisibility(View.VISIBLE);
                }
                if (orderStatusForCompany.getDeliveryComplete().equals("delivery Complete"))
                {
                    holder.deliveryCompleteImg.setVisibility(View.VISIBLE);
                    holder.orderAccepted.setText("delivery Complete");

                }
            }

            else
            {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.orderAccepted.setVisibility(View.GONE);
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").child(orderStatusForCompany.getRandomKey()).child("order");
                    databaseReference.setValue("Order Accepted");
                    holder.checkBox.setVisibility(View.GONE);
                    holder.orderAccepted.setText(orderStatusForCompany.getOrder());
                    holder.orderAccepted.setVisibility(View.VISIBLE);
                }
            });





        }
        if (key.equals("DeliveryMan")) {

            holder.feedback.setVisibility(View.GONE);
            if (orderStatusForCompany.getPacking().equals("packing..."))
            {holder.packing.setVisibility(View.GONE);}
            if (orderStatusForCompany.getDelivery_trak().equals("Delivering Now"))
            {holder.delivering.setVisibility(View.GONE);}
            if (orderStatusForCompany.getDeliveryComplete().equals("delivery Complete"))
            {holder.deliveryComplete.setVisibility(View.GONE);}

            if (orderStatusForCompany.getOrder().equals("Order Accepted"))
            {
                holder.linearLayoutForDelivery.setVisibility(View.VISIBLE);
                holder.checkBox.setVisibility(View.GONE);
                holder.orderAccepted.setText(orderStatusForCompany.getOrder());
                holder.orderAccepted.setVisibility(View.VISIBLE);
                holder.linearLayoutForDelivery.setVisibility(View.VISIBLE);


                holder.packing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").child(orderStatusForCompany.getRandomKey()).child("packing");
                        databaseReference.setValue("packing...");
                        holder.packing.setVisibility(View.GONE);
                    }
                });

                holder.isbeingSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").child(orderStatusForCompany.getRandomKey()).child("delivery_trak");
                        databaseReference.setValue("Delivering Now");
                        holder.isbeingSent.setVisibility(View.GONE);
                    }
                });

                holder.deliveryComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").child(orderStatusForCompany.getRandomKey()).child("deliveryComplete");
                        databaseReference.setValue("delivery Complete");
                        holder.deliveryComplete.isChecked();
                    }
                });


            }

            else
            {
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.orderAccepted.setVisibility(View.GONE);
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").child(orderStatusForCompany.getRandomKey()).child("order");
                    databaseReference.setValue("Order Accepted");
                    holder.checkBox.setVisibility(View.GONE);
                    holder.orderAccepted.setText(orderStatusForCompany.getOrder());
                    holder.orderAccepted.setVisibility(View.VISIBLE);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return orderStatusForCompanyArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView orderName, orderPrice, orderQuantity, orderTotal, orderAccepted;
        public CheckBox checkBox,packing,isbeingSent,deliveryComplete;
        public Button message;
        public LinearLayout linearLayoutForDelivery;
        public ImageView orderAccept,pendding,delivering,packingForImae,deliveryCompleteImg;
        public TextView feedback;
        public TextView customerInfo;
        public TextView date,companysName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderName = itemView.findViewById(R.id.productNameForViewOrder);
            orderPrice = itemView.findViewById(R.id.productPriceForViewOrder);
            orderQuantity = itemView.findViewById(R.id.productQuantityViewOrder);
            orderTotal = itemView.findViewById(R.id.productTotalViewOrder);
            checkBox = itemView.findViewById(R.id.checkBoxId);
            message = itemView.findViewById(R.id.buttonForMessageId);
            orderAccepted = itemView.findViewById(R.id.orderAcceptedId);


            packing = itemView.findViewById(R.id.packing);
            isbeingSent = itemView.findViewById(R.id.isBeingSent);
            deliveryComplete = itemView.findViewById(R.id.deliveryComplete);
            linearLayoutForDelivery = itemView.findViewById(R.id.linearLayoutForDelivery);


            orderAccept=itemView.findViewById(R.id.deliveryAccept);
            pendding = itemView.findViewById(R.id.pendin);
            delivering = itemView.findViewById(R.id.delivering);
            packingForImae = itemView.findViewById(R.id.packingImage);
            deliveryCompleteImg = itemView.findViewById(R.id.deliveryCompleteImg);
            feedback = itemView.findViewById(R.id.feedback);
            customerInfo = itemView.findViewById(R.id.productCustomerInfoForViewOrder);

            date = itemView.findViewById(R.id.dateViewOrder);
            companysName = itemView.findViewById(R.id.companysNameForViewOrder);

        }
    }
}
