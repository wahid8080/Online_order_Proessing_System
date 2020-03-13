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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfieldservice.ModelClasses.CompanysInfo;
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


public class ProfileForCompany extends AppCompatActivity {


    ImageView profileImage, coverImage, licenceImage;
    TextView name, email, area, licence,viewCompanysDetails;
    DatabaseReference databaseReference;
    FirebaseUser user;
    private StorageReference folder;

    private static final int PIC_IMAGE_REQUEST = 1;
    private static final int PIC_IMAGE_REQUEST_COVER = 2;
    private static final int PIC_IMAGE_REQUEST_FOR_LICENCE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_company);

        folder = FirebaseStorage.getInstance().getReference().child("CompanyProfilePicture");

        name = findViewById(R.id.nameForCompany);
        email = findViewById(R.id.emailForCompany);
        area = findViewById(R.id.areaForCompany);
        licence = findViewById(R.id.licenceIdForCompany);
        viewCompanysDetails = findViewById(R.id.viewCompanysDetail);

        profileImage = findViewById(R.id.profileForCompany);
        coverImage = findViewById(R.id.coverForCompany);
        licenceImage = findViewById(R.id.licencePhotocopyForCompany);
        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CompanysInfo companysInfo = dataSnapshot.getValue(CompanysInfo.class);

                try {
                    name.setText(companysInfo.getcName());
                    area.setText("Location " + companysInfo.getcLocation());
                    email.setText("Email " + user.getEmail());
                    licence.setText("Licence " + companysInfo.getcLicence());
                    viewCompanysDetails.setText(companysInfo.getCompanysDetails());


                    if (companysInfo.getProfilePic() != null) {
                        Picasso.get().load(companysInfo.getProfilePic()).into(profileImage);
                    }
                    if (companysInfo.getCoverPic() != null) {
                        Picasso.get().load(companysInfo.getCoverPic()).into(coverImage);
                    }
                    if (companysInfo.getLicenceImage() != null) {
                        Picasso.get().load(companysInfo.getLicenceImage()).into(licenceImage);
                    } else {
                        Toast.makeText(ProfileForCompany.this, "Upload pic", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    startActivity(new Intent(ProfileForCompany.this, SubmitCompanyInfo.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileForCompany.this, Login.class));
        finish();
    }

    public void shopList(View view) {
        startActivity(new Intent(ProfileForCompany.this, ViewShopKepper.class));
    }

    public void submitProduct(View view) {
        Toast.makeText(ProfileForCompany.this, name.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SubmitProduct.class);
        intent.putExtra("key", name.getText().toString());
        startActivity(intent);
    }

    public void chooseLicenceNumberphotocopy(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST_FOR_LICENCE);

    }

    public void chooseProfileImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST);


    }

    public void chooseCoverImage(View view) {

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
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company").child(user.getUid()).child("ProfilePic");
                            databaseReference.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileForCompany.this, "Profile Picture Uploaded", Toast.LENGTH_SHORT).show();

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
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company").child(user.getUid()).child("CoverPic");

                            databaseReference.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileForCompany.this, "Cover Picture Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            });
        }

        if (requestCode == PIC_IMAGE_REQUEST_FOR_LICENCE && resultCode == RESULT_OK) {
            Uri image = data.getData();

            final StorageReference imageName = folder.child("profile" + image.getLastPathSegment());

            imageName.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company").child(user.getUid()).child("LicenceImage");
                            databaseReference.setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileForCompany.this, "Licence Image Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });

                }
            });


        }

    }

    public void uploadCompanysDetails(View view) {
        LinearLayout linearLayout = findViewById(R.id.linearLayoutForAddDetails);
        linearLayout.setVisibility(View.VISIBLE);
        TextView viewCompanysDetail = findViewById(R.id.viewCompanysDetail);
        viewCompanysDetail.setVisibility(View.GONE);
    }

    public void AddDetailsCompany(View view) {
        EditText writeDetails = findViewById(R.id.uploadCompanysDetail);
        String details = writeDetails.getText().toString();
        if (!details.equals(""))
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company").child(user.getUid()).child("CompanysDetails");
            databaseReference.setValue(details).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    LinearLayout linearLayout = findViewById(R.id.linearLayoutForAddDetails);
                    linearLayout.setVisibility(View.GONE);

                    TextView viewCompanysDetail = findViewById(R.id.viewCompanysDetail);
                    viewCompanysDetail.setVisibility(View.VISIBLE);

                }
            });
        } else
        {
            Toast.makeText(ProfileForCompany.this,"Write somthing about company",Toast.LENGTH_SHORT).show();
        }

    }

    public void addEmpolyee(View view) {
        Intent intent = new Intent(ProfileForCompany.this, LoginForDeliveryMan.class);
        intent.putExtra("companyName",name.getText().toString());
        intent.putExtra("companyId",user.getUid());
        startActivity(intent);

    }

    public void ViewOrderByCustomer(View view) {

        Intent intent = new Intent(ProfileForCompany.this,ViewCustomerOrder.class);
        intent.putExtra("key","company");
        startActivity(intent);
    }

    public void productList(View view) {
        Intent intent = new Intent(ProfileForCompany.this,ViewProduct.class);
        intent.putExtra("key",name.getText().toString());
        intent.putExtra("companyKey","company");
        startActivity(intent);
    }

    public void jobs(View view) {
        startActivity(new Intent(ProfileForCompany.this,Jobs.class));
    }

    public void edit(View view) {
        startActivity(new Intent(ProfileForCompany.this,SubmitCompanyInfo.class));
    }
}
