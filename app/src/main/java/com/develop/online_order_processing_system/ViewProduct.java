package com.develop.online_order_processing_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.develop.online_order_processing_system.Adepter.ProdectListAdepter;
import com.develop.online_order_processing_system.ModelClasses.ProductUpload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProduct extends AppCompatActivity {


    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<ProductUpload> productUploadArrayList;
    ProdectListAdepter prodectListAdepter;
    FirebaseUser user;
    FloatingActionButton orderButton;
    private  String key;
    String companyKey;

    SearchView productSearch;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        key = getIntent().getStringExtra("key");
        companyKey = getIntent().getStringExtra("companyKey");

        recyclerView = findViewById(R.id.recyclerViewForProduct);
        orderButton = findViewById(R.id.orderButtonId);
        productSearch = findViewById(R.id.productSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (companyKey.equals("company"))
        {
            orderButton.setVisibility(View.GONE);
        }
        if (companyKey.equals("shopkeeper"))
        {
            orderButton.setVisibility(View.VISIBLE);

        }

        Toast.makeText(ViewProduct.this,companyKey,Toast.LENGTH_SHORT).show();
        databaseReference = FirebaseDatabase.getInstance().getReference("Product").child(key);
        productUploadArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    ProductUpload m = dataSnapshot1.getValue(ProductUpload.class);
                    productUploadArrayList.add(m);

                }

                prodectListAdepter = new ProdectListAdepter(ViewProduct.this,productUploadArrayList, companyKey,key);
                recyclerView.setAdapter(prodectListAdepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prodectListAdepter.getTotal()==0)
                {
                    Toast.makeText(ViewProduct.this,"Choose Item",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String total = String.valueOf(prodectListAdepter.getTotal());
                    Intent intent = new Intent(ViewProduct.this,OrderDetails.class);
                    intent.putExtra("total",total);
                    intent.putExtra("companysName",companyKey);
                    intent.putExtra("companysId",key);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onStart() {

        if (productSearch!=null)
        {
            productSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return false;
                }
            });
        }
        super.onStart();
    }

    private void search(String str) {
        ArrayList<ProductUpload> myList = new ArrayList<>();
        for (ProductUpload object : productUploadArrayList)
        {
            if (object.getProductName().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        ProdectListAdepter prodectListAdepter = new ProdectListAdepter(myList,companyKey,key,"empty",ViewProduct.this);
        recyclerView.setAdapter(prodectListAdepter);
    }
}
