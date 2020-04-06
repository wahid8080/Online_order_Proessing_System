package com.nishu.inventory_management_and_order_processing_system;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.ImageConvater;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.ProductUpload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static java.lang.String.valueOf;

public class SubmitProduct extends AppCompatActivity {

    EditText productName,productPrice,productDes,productOffer;
    Button chooseImg,submitInfo;
    private final static int  PIC_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String key;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ImageConvater  imageConvater;
    boolean isCapture;
    String stringImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_product);
        imageConvater = new ImageConvater();

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


        submitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();
                String price = productPrice.getText().toString();
                String desc = productDes.getText().toString();
                String offer = productOffer.getText().toString();

                if (validate(v) && !offer.isEmpty())
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Product").child(key).child(name);
                    ProductUpload productUpload = new ProductUpload(name,desc,price,stringImage,user.getUid(),offer);
                    databaseReference.setValue(productUpload);
                    Toast.makeText(SubmitProduct.this,"Upload Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SubmitProduct.this,ProfileForCompany.class));
                } else if (validate(v) && offer.isEmpty())
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Product").child(key).child(name);
                    ProductUpload productUpload = new ProductUpload(name,desc,price,stringImage,user.getUid(),"0");
                    databaseReference.setValue(productUpload);
                    Toast.makeText(SubmitProduct.this,"Upload Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SubmitProduct.this,ProfileForCompany.class));
                }
            }
        });


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
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                stringImage = imageConvater.imageToString(bitmap);

                isCapture = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate(final View view) {
        boolean valid = true;
        String name = productName.getText().toString();
        String price = productPrice.getText().toString();
        String desc = productDes.getText().toString();

        if (name.isEmpty()) {
            Snackbar.make(view, "Input Product Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (price.isEmpty()) {
            Snackbar.make(view, "Input Price", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (desc.isEmpty()) {
            Snackbar.make(view, "Input Product Description", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (!isCapture) {
            Snackbar.make(view, "Input Picture", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        return valid;
    }


}
