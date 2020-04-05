package com.nishu.inventory_management_and_order_processing_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nishu.inventory_management_and_order_processing_system.ModelClasses.DeliveryManInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginForDeliveryMan extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String companysId,companysName;
    private EditText mEmail,mPass;
    private EditText userName, nid, phone, distrect, area;
    private Button clickForSingUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_delivery_man);


        companysId = getIntent().getStringExtra("companyId");
        companysName = getIntent().getStringExtra("companyName");
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mEmail = findViewById(R.id.emailForRegId);
        mPass = findViewById(R.id.passForReg);
        userName = findViewById(R.id.deliveryManUserNameId);
        nid = findViewById(R.id.deliveryManNid);
        phone = findViewById(R.id.deliveryManPhoneNumberId);
        distrect = findViewById(R.id.deliveryManDistrict);
        area = findViewById(R.id.deliveryManAriaThanaId);

        clickForSingUp = findViewById(R.id.clickForSingUp);


    }

    public void singupButton(View view) {
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();
        String name = userName.getText().toString();
        String Nid = nid.getText().toString();
        String phoneNo = phone.getText().toString();
        String dis = distrect.getText().toString();
        String Area = area.getText().toString();

        singin(email,pass,view,name,Nid,phoneNo,dis,Area);
    }

    private void singin(final String email, String password, final View view, final String name, final String Nid, final String phone, final String dis, final String area) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DeliveryManInfo deliveryManInfo = new DeliveryManInfo(name,Nid,phone,dis,area,companysId,companysName);

                            FirebaseDatabase.getInstance().getReference("UserForDeliveryMan").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(deliveryManInfo);


                            FirebaseDatabase.getInstance().getReference("Status").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliveryMan").setValue("deliveryMan");
                            FirebaseDatabase.getInstance().getReference("Status").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("shopkeeper").setValue("false");
                            FirebaseDatabase.getInstance().getReference("Status").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("company").setValue("false");
                            Toast.makeText(LoginForDeliveryMan.this,"Registration Success",Toast.LENGTH_LONG).show();
                            clickForSingUp.setVisibility(View.INVISIBLE);

                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(view, "Registration Field", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                    }
                });

    }

}
