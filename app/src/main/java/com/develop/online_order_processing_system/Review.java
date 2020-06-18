package com.develop.online_order_processing_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.develop.online_order_processing_system.Adepter.ReviewAdepter;
import com.develop.online_order_processing_system.ModelClasses.Feedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Review extends AppCompatActivity {

    RecyclerView feedbackComment;
    ArrayList<Feedback> feedbackArrayList;
    ReviewAdepter reviewAdepter;
    DatabaseReference mData;
    String comapnysTitle;
    String shopName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        comapnysTitle =getIntent().getStringExtra("companysId");
        feedbackComment = findViewById(R.id.feedbackComment);
        feedbackArrayList = new ArrayList<>();


        feedbackComment.setLayoutManager(new LinearLayoutManager(this));
        mData = FirebaseDatabase.getInstance().getReference("Feedback").child(comapnysTitle);

        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbackArrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Feedback m = dataSnapshot1.getValue(Feedback.class);
                    feedbackArrayList.add(m);

                }

                reviewAdepter = new ReviewAdepter(feedbackArrayList, Review.this,shopName);
                feedbackComment.setAdapter(reviewAdepter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
