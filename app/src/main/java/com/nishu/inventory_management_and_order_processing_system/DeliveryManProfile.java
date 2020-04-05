package com.nishu.inventory_management_and_order_processing_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nishu.inventory_management_and_order_processing_system.ModelClasses.DeliveryManInfo;
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
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.ImageConvater;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DeliveryManProfile extends AppCompatActivity {

    private TextView name, nid, phone, distict, area, email;
    private ImageView profileImage;
    private static final int PIC_IMAGE_REQUEST_Profile = 10;
    private StorageReference folder;
    private FirebaseUser user;
    private Bitmap bitmap;
    private ImageConvater imageConvater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_man_profile);
        imageConvater = new ImageConvater();

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
                        Bitmap returnImage = imageConvater.StringToBitMap(deliveryManInfo.getDeliveryManProfile());
                        profileImage.setImageBitmap(returnImage);
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
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profileImage.setImageBitmap(bitmap);
                String stringImage = imageConvater.imageToString(bitmap);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserForDeliveryMan").child(user.getUid()).child("deliveryManProfile");
                databaseReference.setValue(stringImage);

            } catch (IOException e) {
                e.printStackTrace();
            }



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
