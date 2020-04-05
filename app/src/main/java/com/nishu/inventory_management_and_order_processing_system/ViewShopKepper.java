package com.nishu.inventory_management_and_order_processing_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nishu.inventory_management_and_order_processing_system.Adepter.ShopKepperListAdepter;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.ShopKeeperInfo;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewShopKepper extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<ShopKeeperInfo> shopKeeperInfoArrayList;
    ShopKepperListAdepter kepperListAdepter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop_kepper);

        recyclerView = findViewById(R.id.recyclerViewForShopKeeper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper");
        shopKeeperInfoArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ShopKeeperInfo m = dataSnapshot1.getValue(ShopKeeperInfo.class);
                    shopKeeperInfoArrayList.add(m);
                }

                kepperListAdepter = new ShopKepperListAdepter(ViewShopKepper.this,shopKeeperInfoArrayList);
                recyclerView.setAdapter(kepperListAdepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void shopMap(View view) {
        startActivity(new Intent(ViewShopKepper.this,MapsActivity.class));
    }
}
