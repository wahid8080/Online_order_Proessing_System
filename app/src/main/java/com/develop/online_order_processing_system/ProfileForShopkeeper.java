package com.develop.online_order_processing_system;

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

import com.develop.online_order_processing_system.ModelClasses.ImageConvater;
import com.develop.online_order_processing_system.ModelClasses.ShopKeeperInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ProfileForShopkeeper extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser user;

    TextView name, area, road, house, email, phone, licence;
    ImageView cover, profile;
    private ImageConvater imageConvater;

    private static final int PIC_IMAGE_REQUEST = 1;
    private static final int PIC_IMAGE_REQUEST_COVER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_shopkeeper);

        imageConvater = new ImageConvater();
        name = findViewById(R.id.nameForShop);
        area = findViewById(R.id.areaForShop);
        road = findViewById(R.id.roadForShop);
        house = findViewById(R.id.houseNumberForShop);
        email = findViewById(R.id.emailForShoP);
        phone = findViewById(R.id.phoneForShop);
        licence = findViewById(R.id.registrationIdForShop);
        cover = findViewById(R.id.coverForShop);
        profile = findViewById(R.id.profileForShop);
        user = FirebaseAuth.getInstance().getCurrentUser();


        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShopKeeperInfo shopKeeperInfo = dataSnapshot.getValue(ShopKeeperInfo.class);

                try {
                    name.setText("Name : "+shopKeeperInfo.getsName());
                    area.setText("Area : "+shopKeeperInfo.getsArea());
                    road.setText("Road No : "+shopKeeperInfo.getsRoad());
                    house.setText("House No : "+shopKeeperInfo.getsNumber());
                    email.setText("Email : "+user.getEmail());
                    phone.setText("Phone : "+shopKeeperInfo.getsPhone());
                    licence.setText("Licence : "+shopKeeperInfo.getsLicence());


                    if (shopKeeperInfo.getProfilePic()!=null)
                    {
                        Bitmap returnImage = imageConvater.StringToBitMap(shopKeeperInfo.getProfilePic());
                        profile.setImageBitmap(returnImage);

                    } if (shopKeeperInfo.getCoverPic()!=null)
                    {
                        Bitmap returnImage = imageConvater.StringToBitMap(shopKeeperInfo.getCoverPic());
                        cover.setImageBitmap(returnImage);
                    }
                    else
                    {
                        Toast.makeText(ProfileForShopkeeper.this,"Upload pic",Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e)
                {
                    startActivity(new Intent(ProfileForShopkeeper.this, SubmitShopKeeperInfo.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void companyList(View view) {
        Intent intent = new Intent(ProfileForShopkeeper.this,ViewCompany.class);
        intent.putExtra("companyKey","shopkeeper");
        intent.putExtra("shopName",name.getText().toString());
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileForShopkeeper.this, Login.class));
        finish();
    }


    public void chooseShopKeeperProfile(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST);


    }

    public void chooseShopKeeperCover(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST_COVER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profile.setImageBitmap(bitmap);
                String stringImage = imageConvater.imageToString(bitmap);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid()).child("ProfilePic");
                databaseReference.setValue(stringImage);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (requestCode == PIC_IMAGE_REQUEST_COVER && resultCode == RESULT_OK) {

            Uri path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                cover.setImageBitmap(bitmap);
                String stringImage = imageConvater.imageToString(bitmap);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid()).child("CoverPic");
                databaseReference.setValue(stringImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void orderView(View view) {
        Intent intent = new Intent(ProfileForShopkeeper.this,ViewCustomerOrder.class);
        intent.putExtra("key","shopkeeper");
        startActivity(intent);
    }

    public void edit(View view) {
        startActivity(new Intent(ProfileForShopkeeper.this,SubmitShopKeeperInfo.class));
    }
}
