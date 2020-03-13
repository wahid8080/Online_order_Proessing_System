package com.example.smartfieldservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.example.smartfieldservice.ModelClasses.ShopKeeperInfo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitShopKeeperInfo extends AppCompatActivity {

    EditText sName,sArea,sPhone,sNumber,sRoad,sLicence;
    FirebaseUser user;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_shop_keeper_info);

        sName = findViewById(R.id.shopsName);
        sArea = findViewById(R.id.shopsArea);
        sPhone = findViewById(R.id.shopsPhone);
        sNumber = findViewById(R.id.shopsNumber);
        sRoad = findViewById(R.id.shopRoadNumber);
        sLicence = findViewById(R.id.shopsLicence);
        user = FirebaseAuth.getInstance().getCurrentUser();


        databaseReference2 = FirebaseDatabase.getInstance().getReference("Status").child(user.getUid()).child("shopkeeper");
        databaseReference2.setValue("ShopKeeper");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Status").child(user.getUid()).child("company");
        databaseReference3.setValue("false");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Status").child(user.getUid()).child("deliveryMan");
        databaseReference3.setValue("false");

    }

    public void submitShopsInfo(View view) {

        if (validate(view)==true)
        {
            submitInfo();
            startActivity(new Intent(SubmitShopKeeperInfo.this,ProfileForShopkeeper.class));
            finish();

        } else
        {
            validate(view);
        }

    }

    private void submitInfo() {
        String name=sName.getText().toString();
        String area=sArea.getText().toString();
        String phone=sPhone.getText().toString();
        String number=sNumber.getText().toString();
        String road=sRoad.getText().toString();
        String licence = sLicence.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("ShopKeeper").child(user.getUid());
        ShopKeeperInfo shopKeeperInfo = new ShopKeeperInfo(name,phone,area,road,number,licence);
        databaseReference.setValue(shopKeeperInfo);
        finish();
    }

    public boolean validate(final View view) {
        boolean valid = true;
        String name=sName.getText().toString();
        String area=sArea.getText().toString();
        String phone=sPhone.getText().toString();
        String number=sNumber.getText().toString();
        String road=sRoad.getText().toString();
        if (name.isEmpty()) {
            Snackbar.make(view, "Input Your Shops Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (area.isEmpty()) {
            Snackbar.make(view, "Input Your Shops Area", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (phone.isEmpty()) {
            Snackbar.make(view, "Input Your Shops Phone", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (number.isEmpty()) {
            Snackbar.make(view, "Input Your Shops Number", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (road.isEmpty()) {
            Snackbar.make(view, "Input Your Shops Road", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return valid;
    }

}
