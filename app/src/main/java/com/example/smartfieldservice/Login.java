package com.example.smartfieldservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartfieldservice.ModelClasses.CompanysInfo;
import com.example.smartfieldservice.ModelClasses.DeliveryManInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText mEmail, mPass;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emailForLogin);
        mPass = findViewById(R.id.PassForLogin);
        mAuth = FirebaseAuth.getInstance();
    }

    public void goToLogin(final View view) {
        progressDialog = new ProgressDialog(this);

        final String email = mEmail.getText().toString().trim();
        final String pass = mPass.getText().toString();

        if (validate() == true) {
            progressDialog.setMessage("Try to Login");
            progressDialog.setTitle("Processing...");
            progressDialog.show();
            login(email, pass, view);

        } else {
            validate();
        }

    }

    private boolean validate() {

        boolean valid = true;
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString();

        if (email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            return false;
        } else {
            mEmail.setError(null);
        }

        if (pass.isEmpty() ){
            mPass.setError("enter your correct password");
            return false;
        } else {
            mPass.setError(null);
        }
        return valid;

    }

    public void goToSingUp(View view) {
        startActivity(new Intent(Login.this, Registration.class));
    }


    private void login(final String email, String password, final View view) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            Intent intent = new Intent(Login.this, Empty.class);
                            startActivity(intent);
                            Toast.makeText(Login.this, "User Login Successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.cancel();
                            Snackbar.make(view, "Check your Email Or Password", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!=null)
        {
            startActivity(new Intent(Login.this,Empty.class));
            finish();

        }
    }
}
