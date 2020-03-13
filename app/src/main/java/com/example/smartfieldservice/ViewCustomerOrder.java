package com.example.smartfieldservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.smartfieldservice.Adepter.ProdectListAdepter;
import com.example.smartfieldservice.Adepter.ViewOrderByCustomerAdepter;
import com.example.smartfieldservice.ModelClasses.OrderStatusForCompany;
import com.example.smartfieldservice.ModelClasses.ProductUpload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCustomerOrder extends AppCompatActivity {


    DatabaseReference databaseReference;
    RecyclerView recyclerViewForTotalCustomerOrder,recyclerViewForCustomerOrderProcess,recyclerViewForCustomerOrderComplete;
    ArrayList<OrderStatusForCompany> orderStatusForCompanyCompleteArrayList,orderStatusForProcessCompanyArrayList,orderStatusForCompanyArrayList;
    ViewOrderByCustomerAdepter viewOrderByCustomerAdepter;
    FirebaseUser user;

    String companysId = "";
    String key;

    Button orderComplete,processOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_order);

        orderComplete = findViewById(R.id.completeOrder);
        processOrder = findViewById(R.id.orderProcessed);
        companysId = getIntent().getStringExtra("companysId");
        recyclerViewForTotalCustomerOrder = findViewById(R.id.recyclerViewForTotalCustomerOrder);
        recyclerViewForCustomerOrderComplete = findViewById(R.id.recyclerViewForCustomerOrderComplete);
        recyclerViewForCustomerOrderProcess = findViewById(R.id.recyclerViewForCustomerOrderProcess);

        orderStatusForCompanyCompleteArrayList = new ArrayList<>();
        orderStatusForProcessCompanyArrayList = new ArrayList<>();
        orderStatusForCompanyArrayList=new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerViewForTotalCustomerOrder.setLayoutManager(new LinearLayoutManager(ViewCustomerOrder.this));
        recyclerViewForCustomerOrderComplete.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewForCustomerOrderProcess.setLayoutManager(new LinearLayoutManager(this));
        key = getIntent().getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference("OrderCustomerVaucher");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (key.equals("shopkeeper"))
                {
                    orderStatusForCompanyArrayList.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                        if (m.getCustomerId().equals(user.getUid()))
                        {
                            orderStatusForCompanyArrayList.add(m);
                        }

                    }
                }
                if (key.equals("company"))
                {
                    orderStatusForCompanyArrayList.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);

                        orderStatusForCompanyArrayList.add(m);
                        if (m.getCompanysId().equals(user.getUid()))
                        {
                            Log.d("TAG",m.getCompanysId()+"  from database");
                            Log.d("TAG",user.getUid()+" from u id");
                        }

                    }
                }
                if (key.equals("DeliveryMan"))
                {
                    orderStatusForCompanyArrayList.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                        if (m.getCompanysId().equals(companysId))
                        {
                            orderStatusForCompanyArrayList.add(m);
                        }

                    }
                }

                viewOrderByCustomerAdepter = new ViewOrderByCustomerAdepter(ViewCustomerOrder.this,orderStatusForCompanyArrayList ,key);
                recyclerViewForTotalCustomerOrder.setAdapter(viewOrderByCustomerAdepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        orderComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        if (key.equals("shopkeeper"))
                        {
                            orderStatusForCompanyCompleteArrayList.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                                if (m.getCustomerId().equals(user.getUid())&& m.getDeliveryComplete().equals("delivery Complete"))
                                {
                                    orderStatusForCompanyCompleteArrayList.add(m);
                                }

                            }
                        }
                        if (key.equals("company"))
                        {
                            orderStatusForCompanyCompleteArrayList.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                                if (m.getCompanysId().equals(user.getUid())&& m.getDeliveryComplete().equals("delivery Complete"))
                                {
                                    orderStatusForCompanyCompleteArrayList.add(m);
                                }

                            }
                        }
                        if (key.equals("DeliveryMan"))
                        {
                            orderStatusForCompanyCompleteArrayList.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                                if (m.getCompanysId().equals(companysId)&& m.getDeliveryComplete().equals("delivery Complete"))
                                {
                                    orderStatusForCompanyCompleteArrayList.add(m);
                                }

                            }
                        }

                        viewOrderByCustomerAdepter = new ViewOrderByCustomerAdepter(ViewCustomerOrder.this,orderStatusForCompanyCompleteArrayList ,key);
                        recyclerViewForCustomerOrderComplete.setVisibility(View.VISIBLE);
                        recyclerViewForCustomerOrderComplete.setAdapter(viewOrderByCustomerAdepter);
                        recyclerViewForCustomerOrderProcess.setVisibility(View.GONE);
                        recyclerViewForTotalCustomerOrder.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        processOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (key.equals("shopkeeper"))
                        {
                            orderStatusForProcessCompanyArrayList.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                                if (m.getCustomerId().equals(user.getUid())&& !m.getDeliveryComplete().equals("delivery Complete"))
                                {
                                    orderStatusForProcessCompanyArrayList.add(m);
                                }

                            }
                        }
                        if (key.equals("company"))
                        {
                            orderStatusForProcessCompanyArrayList.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                                if (m.getCompanysId().equals(user.getUid())&& !m.getDeliveryComplete().equals("delivery Complete"))
                                {
                                    orderStatusForProcessCompanyArrayList.add(m);
                                }

                            }
                        }
                        if (key.equals("DeliveryMan"))
                        {
                            orderStatusForProcessCompanyArrayList.clear();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                OrderStatusForCompany m = dataSnapshot1.getValue(OrderStatusForCompany.class);
                                if (m.getCompanysId().equals(companysId)&& !m.getDeliveryComplete().equals("delivery Complete"))
                                {
                                    orderStatusForProcessCompanyArrayList.add(m);
                                }

                            }
                        }

                        viewOrderByCustomerAdepter = new ViewOrderByCustomerAdepter(ViewCustomerOrder.this,orderStatusForProcessCompanyArrayList ,key);
                        recyclerViewForCustomerOrderProcess.setVisibility(View.VISIBLE);
                        recyclerViewForCustomerOrderProcess.setAdapter(viewOrderByCustomerAdepter);
                        recyclerViewForTotalCustomerOrder.setVisibility(View.GONE);
                        recyclerViewForCustomerOrderComplete.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

}
