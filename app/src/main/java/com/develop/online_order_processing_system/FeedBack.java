package com.develop.online_order_processing_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.develop.online_order_processing_system.ModelClasses.Feedback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedBack extends AppCompatActivity {

    EditText feedback;
    String companysTitle,customerId,companysId;
    RatingBar ratingBar;
    Button submitFeedback;
    ImageView imageForLike;
    ImageView imageforUnlike;
    int total_like;
    int total_unlike;
    float total_rating;
    String shopKeeperName;

    EditText feedbackComment;

    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        feedback = findViewById(R.id.feedbackComment);

        user = FirebaseAuth.getInstance().getCurrentUser();

        companysTitle = getIntent().getStringExtra("companysTitle");
        customerId = getIntent().getStringExtra("customerId");
        companysId = getIntent().getStringExtra("companysId");
        ratingBar = findViewById(R.id.ratingStar);
        submitFeedback = findViewById(R.id.submitFeedback);
        imageForLike = findViewById(R.id.like);
        imageforUnlike = findViewById(R.id.unlike);
        feedbackComment = findViewById(R.id.feedbackComment);


        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float ratingStar = ratingBar.getRating();
                String comment = feedbackComment.getText().toString();

                DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Feedback").child(companysTitle).child(customerId);
                Feedback feedback = new Feedback(ratingStar,comment,shopKeeperName,total_like,total_unlike);
                mData.setValue(feedback);

                Toast.makeText(FeedBack.this,"rating send",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void like(View view) {
        int  like = 1;
        total_like = total_like+like;
        Toast.makeText(FeedBack.this,"Liked",Toast.LENGTH_SHORT).show();
        DatabaseReference mLike = FirebaseDatabase.getInstance().getReference("User").child("Company").child(companysId).child("like");
        mLike.setValue(total_like);

    }

    public void unLike(View view) {
        int  unlike = 1;
        total_unlike = total_unlike+unlike;
        Toast.makeText(FeedBack.this,"Unlike",Toast.LENGTH_SHORT).show();
        DatabaseReference mLike = FirebaseDatabase.getInstance().getReference("User").child("Company").child(companysId).child("unlike");
        mLike.setValue(total_unlike);
    }

}
