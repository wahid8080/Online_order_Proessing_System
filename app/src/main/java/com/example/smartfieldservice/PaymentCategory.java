package com.example.smartfieldservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_category);
    }

    public void bKash(View view) {
        startActivity(new Intent(PaymentCategory.this,bKask.class));

    }

    public void MasterCard(View view) {
        startActivity(new Intent(PaymentCategory.this,Payment.class));
    }

    public void cashOnDelivery(View view) {

    }
}
