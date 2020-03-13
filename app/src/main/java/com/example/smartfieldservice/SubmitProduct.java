package com.example.smartfieldservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartfieldservice.ModelClasses.ProductUpload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static java.lang.String.valueOf;

public class SubmitProduct extends AppCompatActivity {

    EditText productName,productPrice,productDes,productOffer;
    Button chooseImg,submitInfo;
    private final static int  PIC_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String key;
    private StorageReference folder;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_product);

        productName = findViewById(R.id.productUploadName);
        productPrice = findViewById(R.id.productUploadPrice);
        productDes = findViewById(R.id.productUploadDicId);
        chooseImg = findViewById(R.id.productUploadChooseImage);
        submitInfo = findViewById(R.id.submitProductInfo);
        productOffer = findViewById(R.id.productUploadOffer);
        key = getIntent().getStringExtra("key");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        folder = FirebaseStorage.getInstance().getReference().child(key).child("ProductImage");


    }


    public void productSubmit(View view) {


    }

    public void chooseImage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PIC_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {

            Uri image = data.getData();

            final StorageReference imageName = folder.child("product" + image.getLastPathSegment());

            imageName.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //String name = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {
                           submitInfo.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   String name = productName.getText().toString();
                                   String price = productPrice.getText().toString();
                                   String desc = productDes.getText().toString();
                                   String offer = productOffer.getText().toString();


                                   databaseReference = FirebaseDatabase.getInstance().getReference("Product").child(key).child(name);
                                   ProductUpload productUpload = new ProductUpload(name,desc,price,String.valueOf(uri),user.getUid(),offer);
                                   databaseReference.setValue(productUpload);

                                   startActivity(new Intent(SubmitProduct.this,ProfileForCompany.class));
                               }
                           });
                        }
                    });

                }
            });
        }
    }

}
