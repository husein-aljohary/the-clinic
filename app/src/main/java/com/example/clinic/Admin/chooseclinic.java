package com.example.clinic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.example.clinic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class chooseclinic extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Clinic> list;
    MyAdapter2 myAdapter;
    DatabaseReference database;
    StorageReference firebaseStorage;
    ProgressDialog progressDialog;

    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseclinic);



        nav=findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),choosecity.class));
                        overridePendingTransition(0,0);
                        return true;
                    default:
                }
                return true;
            }
        });





        String cityname=getIntent().getStringExtra("the city is ");

        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance().getReference().child("clinicnfo").child(cityname);
        //progress_Dailog();
        list = new ArrayList<>();
        myAdapter = new MyAdapter2(this,list ,new MyAdapter2.Itemclicklistener() {
            @Override
            public void onitemclick(Clinic details) {
                send(cityname,details.getName());
            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(myAdapter);
        firebaseStorage = FirebaseStorage.getInstance().getReference().child("City/").child(cityname);
        firebaseStorage.listAll().addOnCompleteListener(task -> {
            List<StorageReference> tasks = task.getResult().getItems();
            for (int i = 0; i < tasks.size(); i++) {
                StorageReference reference = tasks.get(i);
                reference.getDownloadUrl().addOnCompleteListener(urlTask -> {

                    Uri imageUri = urlTask.getResult();
                    Log.e("--TAG", "onCreate: " + imageUri);
                    //   database.child(a.getName());
                    list.add(new Clinic(reference.getName(), imageUri));

                    if (list.size() == tasks.size()) myAdapter.notifyItemRangeInserted(0,list.size());
                });
            }

        });


    }


    public void send(String message,String message2){
        //Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(chooseclinic.this, choosedepartment.class);
        intent.putExtra("city",message);
        intent.putExtra("clinic",message2);
        startActivity(intent);

    }


    public void onBackPressed(){
        progressDialog.dismiss();

    }
    public void progress_Dailog(){
        progressDialog =new ProgressDialog(chooseclinic.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.activity_splash);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        progressDialog.setCancelable(false);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        },3000);
    }






}