package com.example.smartfieldservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.smartfieldservice.Adepter.CompanysListAdepter;
import com.example.smartfieldservice.Adepter.ProdectListAdepter;
import com.example.smartfieldservice.Adepter.ShopKepperListAdepter;
import com.example.smartfieldservice.ModelClasses.CompanysInfo;
import com.example.smartfieldservice.ModelClasses.ProductUpload;
import com.example.smartfieldservice.ModelClasses.ShopKeeperInfo;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCompany extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<CompanysInfo> companysInfoArrayList;
    CompanysListAdepter companysListAdepter;
    FirebaseUser user;
    SearchView companySearch;
    String companysKey,shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_company);


        companySearch = findViewById(R.id.companySearch);
        companysKey = getIntent().getStringExtra("companyKey");

        shopName = getIntent().getStringExtra("shopName");
        recyclerView = findViewById(R.id.recyclerViewForCompany);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child("Company");
        companysInfoArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    CompanysInfo m = dataSnapshot1.getValue(CompanysInfo.class);
                    companysInfoArrayList.add(m);

                }

                companysListAdepter = new CompanysListAdepter(ViewCompany.this,companysInfoArrayList ,companysKey,shopName,"nothing");
                recyclerView.setAdapter(companysListAdepter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void companysMap(View view) {
        startActivity(new Intent(ViewCompany.this,MapsActivity.class));
    }



    @Override
    public void onStart() {

        if (companySearch!=null)
        {
            companySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ArrayList<CompanysInfo> myList = new ArrayList<>();
        for (CompanysInfo object : companysInfoArrayList)
        {
            if (object.getcName().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        CompanysListAdepter companysListAdepter = new CompanysListAdepter(ViewCompany.this,myList ,companysKey,"hello");
        recyclerView.setAdapter(companysListAdepter);
    }
}
