package com.develop.online_order_processing_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.develop.online_order_processing_system.ModelClasses.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Empty extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Status").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Status status = dataSnapshot.getValue(Status.class);

                if (status.getCompany().equals("Company"))
                {
                    startActivity(new Intent(Empty.this,ProfileForCompany.class));
                }
                else if (status.getShopkeeper().equals("ShopKeeper"))
                {
                    startActivity(new Intent(Empty.this,ProfileForShopkeeper.class));
                }
                else if (status.getDeliveryMan().equals("deliveryMan"))
                {
                    Intent intent = new Intent(Empty.this,DeliveryManProfile.class);
                    startActivity(intent);

                }
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
