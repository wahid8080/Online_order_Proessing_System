package com.nishu.inventory_management_and_order_processing_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nishu.inventory_management_and_order_processing_system.Adepter.ViewOrderVouchersAdepter;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.OrderStatus;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.OrderStatusForCompany;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.ShopKeeperInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderDetails extends AppCompatActivity {


    DatabaseReference databaseReference, mReference;
    RecyclerView recyclerView;
    ArrayList<OrderStatus> orderStatusArrayList;
    ViewOrderVouchersAdepter viewOrderVouchersAdepter;
    FirebaseUser user;
    TextView totalCost;
    String companysName,customerId, total,companysTitle;
    String companysId;
    StringBuilder orderName = new StringBuilder();
    StringBuilder quantity = new StringBuilder();
    StringBuilder productPrice = new StringBuilder();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        recyclerView = findViewById(R.id.recyclerViewForVaucherId);
        orderStatusArrayList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("OrderStatus").child(user.getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        companysName = getIntent().getStringExtra("companysName");
        total = getIntent().getStringExtra("total");
        companysTitle = getIntent().getStringExtra("companysId");
        totalCost = findViewById(R.id.totalCost);
        totalCost.setText(total + "৳");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    OrderStatus m = dataSnapshot1.getValue(OrderStatus.class);
                    orderStatusArrayList.add(m);

                }

                viewOrderVouchersAdepter = new ViewOrderVouchersAdepter(OrderDetails.this, orderStatusArrayList);
                recyclerView.setAdapter(viewOrderVouchersAdepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void goToPayment(View view) {

        for (int i = 0; i < orderStatusArrayList.size(); i++) {
            OrderStatus order = orderStatusArrayList.get(i);
            String orderNameProduct = order.getProductName();
            int orderQuantity = order.getQuantity();
            orderName.append(i + 1 + "No" + "." + orderNameProduct + ", ");
            quantity.append(i + 1 + "No" + "." + String.valueOf(orderQuantity) + ", ");
            productPrice.append(i + 1 + "No" + "." + String.valueOf(order.getPrice()) + " ৳, ");

            companysId = order.getCompanysId();
            customerId = order.getCustomerId();
        }
        mReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").push();

        final String randomKey = mReference.getKey();
        String orderAcceptrd = "";
        String delivery_track = "";
        String packing = "";
        String deliveryComplete = "";

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ShopKeeperInfo shopKeeperInfo = dataSnapshot.getValue(ShopKeeperInfo.class);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher").child(randomKey).child("customerInfo");
                databaseReference.setValue("Name: "+shopKeeperInfo.getsName()+" Phone: "+shopKeeperInfo.getsNumber()+" Area: "+shopKeeperInfo.getsArea()
                        +"Road: "+shopKeeperInfo.getsRoad());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        OrderStatusForCompany orderStatusForCompany = new OrderStatusForCompany(String.valueOf(orderName), String.valueOf(productPrice), String.valueOf(quantity),
                total, user.getUid(), companysId, randomKey, orderAcceptrd, delivery_track, packing, deliveryComplete,date,companysTitle);
        mReference.setValue(orderStatusForCompany);

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("OrderStatus").child(user.getUid());
        databaseReference2.removeValue();

        startActivity(new Intent(OrderDetails.this, PaymentCategory.class));
    }

    public void cancelOrder(View view) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("OrderStatus").child(user.getUid());
        databaseReference.removeValue();
        Intent intent = new Intent(OrderDetails.this, ProfileForShopkeeper.class);
        intent.putExtra("key", companysName);
        intent.putExtra("customerId",customerId);
        startActivity(intent);
    }
}
