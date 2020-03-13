package com.example.smartfieldservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfieldservice.ModelClasses.DeliveryManInfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class DeliveryManProfile extends AppCompatActivity {

    private TextView name, nid, phone, distict, area, email;
    private ImageView profileImage;
    private static final int PIC_IMAGE_REQUEST_Profile = 10;
    private StorageReference folder;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man_profile);

        name = findViewById(R.id.nameForDelivery);
        nid = findViewById(R.id.nIdForDelivery);
        phone = findViewById(R.id.phoneForDelivery);
        distict = findViewById(R.id.disctricForDelivery);
        area = findViewById(R.id.areaForDelivery);
        email = findViewById(R.id.emailForDelivery);

        user = FirebaseAuth.getInstance().getCurrentUser();

        profileImage = findViewById(R.id.profileForDelivery);

        folder = FirebaseStorage.getInstance().getReference().child("DeliveryProfilePicture");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserForDeliveryMan").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DeliveryManInfo deliveryManInfo = dataSnapshot.getValue(DeliveryManInfo.class);

                try {

                    name.setText(deliveryManInfo.getUserName());
                    area.setText("Location: " + deliveryManInfo.getArea());
                    nid.setText("Nid: " + deliveryManInfo.getNid());
                    phone.setText("Phone: " + deliveryManInfo.getPhone());
                    email.setText("Email: " + user.getEmail());
                    distict.setText("Distract: " + deliveryManInfo.getDistrect());


                    if (deliveryManInfo.getDeliveryManProfile() != null) {
                        Picasso.get()
                                .load(deliveryManInfo.getDeliveryManProfile())
                                .into(profileImage);

                    }


                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addProfileImageDelivery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST_Profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST_Profile && resultCode == RESULT_OK) {
            Uri image = data.getData();

            final StorageReference imageName = folder.child("profile" + image.getLastPathSegment());

            imageName.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserForDeliveryMan").child(user.getUid()).child("deliveryManProfile");
                            databaseReference.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(DeliveryManProfile.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                                }

                            });
                        }
                    });

                }
            });


        }


    }

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DeliveryManProfile.this, Login.class));
        finish();
    }

    public void yourOrder(View view) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserForDeliveryMan").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DeliveryManInfo deliveryManInfo = dataSnapshot.getValue(DeliveryManInfo.class);;

                Intent intent = new Intent(DeliveryManProfile.this, ViewCustomerOrder.class);
                intent.putExtra("companysId", deliveryManInfo.getCompanyId());
                intent.putExtra("key","DeliveryMan");
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
