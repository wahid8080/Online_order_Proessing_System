package com.nishu.inventory_management_and_order_processing_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private ProgressDialog progressDialog2;
    private EditText mEmail, mPass, mConPass;
    private FirebaseAuth mAuth;
    private Spinner mSpinner;
    private Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.emailForRegId);
        mPass = findViewById(R.id.passForReg);
        mConPass = findViewById(R.id.confPassForReg);
        mSpinner = findViewById(R.id.spinnerForShopAndCompany);
        progressDialog2 = new ProgressDialog(this);

    }

    public void singupButton(View view) {


        String email = mEmail.getText().toString().trim();
        String password = mPass.getText().toString();

        if (validate(view) == true) {
            progressDialog2.setMessage("Try to Sing in");
            progressDialog2.setTitle("Processing...");
            progressDialog2.show();
            singin(email, password, view);

        } else {
            validate(view);
        }
    }

    private void singin(String email, String password, final View view) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mSpinner.getSelectedItem().equals("Company")) {

                                Intent intent = new Intent(Registration.this, SubmitCompanyInfo.class);
                                intent.putExtra("key",mSpinner.getSelectedItem().toString());
                                startActivity(intent);
                                progressDialog2.dismiss();
                                finish();

                            } else if (mSpinner.getSelectedItem().equals("ShopKeeper")) {
                                Intent intent = new Intent(Registration.this, SubmitShopKeeperInfo.class);
                                intent.putExtra("key",mSpinner.getSelectedItem().toString());
                                startActivity(intent);
                                progressDialog2.dismiss();
                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(view, "Registration Field"+task.getException(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            progressDialog2.cancel();
                        }

                    }
                });

    }


    public boolean validate(final View view) {
        boolean valid = true;
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString();
        String rePass = mConPass.getText().toString();

        if (mSpinner.getSelectedItem().equals("Select Organization")) {
            Snackbar.make(view, "Select Your Organization", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            progressDialog2.cancel();
            return false;
        } else if (email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            return false;
        } else {
            mEmail.setError(null);
        }

        if (pass.length() <= 5) {
            mPass.setError("password too short");
            return false;
        } else if (pass.isEmpty()) {
            mPass.setError("Password is empty");
            return false;
        } else if (!pass.equals(rePass)) {
            mConPass.setError("Password don't match");
            return false;
        } else {
            mConPass.setError(null);
        }
        return valid;
    }


}
