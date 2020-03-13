package com.example.smartfieldservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfieldservice.ModelClasses.ShopKeeperInfo;
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

public class ProfileForShopkeeper extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseUser user;

    TextView name, area, road, house, email, phone, licence;
    ImageView cover, profile;

    private StorageReference folder;

    private static final int PIC_IMAGE_REQUEST = 1;
    private static final int PIC_IMAGE_REQUEST_COVER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_shopkeeper);

        folder = FirebaseStorage.getInstance().getReference().child("ShopKeeperProfilePicture");

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
                        Picasso.get().load(shopKeeperInfo.getProfilePic()).into(profile);

                    } if (shopKeeperInfo.getCoverPic()!=null)
                    {
                        Picasso.get().load(shopKeeperInfo.getCoverPic()).into(cover);
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
            Uri image = data.getData();

            final StorageReference imageName = folder.child("profile" + image.getLastPathSegment());

            imageName.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid()).child("ProfilePic");
                            databaseReference.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileForShopkeeper.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });

                }
            });


        }
        if (requestCode == PIC_IMAGE_REQUEST_COVER && resultCode == RESULT_OK) {
            Uri image = data.getData();

            final StorageReference imageName = folder.child("cover" + image.getLastPathSegment());

            imageName.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid()).child("CoverPic");

                            databaseReference.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileForShopkeeper.this, "Cover Picture Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            });
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
