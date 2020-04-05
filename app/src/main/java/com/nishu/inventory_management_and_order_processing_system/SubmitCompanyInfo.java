package com.nishu.inventory_management_and_order_processing_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nishu.inventory_management_and_order_processing_system.GoogleApi.GPSTracker;
import com.nishu.inventory_management_and_order_processing_system.ModelClasses.CompanysInfo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SubmitCompanyInfo extends AppCompatActivity {

    EditText companysName,companysLocation,companysLicence;
    FirebaseUser user;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    String keyValue;
    private static final int PIC_IMAGE_REQUEST=1;
    private StorageReference folder;
    private double latitute;
    private double lagatitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_company_info);

        folder = FirebaseStorage.getInstance().getReference().child("CompanyLicenceImage");

        keyValue = getIntent().getStringExtra("key");
        companysName = findViewById(R.id.companyName);
        companysLocation = findViewById(R.id.companyLocation);
        companysLicence = findViewById(R.id.companyLicenceId);
        user = FirebaseAuth.getInstance().getCurrentUser();


        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        Location l = gpsTracker.getLocation();

        if (l != null)
        {
            latitute = l.getLatitude();
            lagatitute = l.getLongitude();
        }


        databaseReference2 = FirebaseDatabase.getInstance().getReference("Status").child(user.getUid()).child("ShopKeeper");
        databaseReference2.setValue("false");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Status").child(user.getUid()).child("company");
        databaseReference3.setValue("Company");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Status").child(user.getUid()).child("deliveryMan");
        databaseReference3.setValue("false");

    }

    public void submitcompanysInfo(View view) {

        if (validate(view)==true){
            submitData();
            startActivity(new Intent(SubmitCompanyInfo.this,ProfileForCompany.class));
            finish();
        }
        else {
            validate(view);
        }
    }

    private void submitData() {
        String cName = companysName.getText().toString();
        String cLocation  = companysLocation.getText().toString();
        String cLicence = companysLicence.getText().toString();
        int like = 0;
        int unlike = 0;
        float rating = (float) 0.0;




        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company").child(user.getUid());
        CompanysInfo companysInfo = new CompanysInfo(cName,cLocation,cLicence,like,unlike,rating,latitute,lagatitute);
        databaseReference.setValue(companysInfo);
        finish();
    }

    public boolean validate(final View view) {
        boolean valid = true;
        String cName = companysName.getText().toString();
        String cLocation  = companysLocation.getText().toString();
        String cLicence = companysLicence.getText().toString();

        if (cName.isEmpty()) {
            Snackbar.make(view, "Input Company's Name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        else if (cLicence.isEmpty()) {
            Snackbar.make(view, "Input Company's Licence", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else if (cLocation.isEmpty()) {
            Snackbar.make(view, "Input Company's Location", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }

        return valid;
    }

}
