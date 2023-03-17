package com.example.clinic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.example.clinic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class choosecity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<City> list;
    MyAdapter myAdapter;
    DatabaseReference database;
    StorageReference firebaseStorage;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosecity);










        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        progress_Dailog();

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list, new MyAdapter.Itemclicklistener() {
            @Override
            public void onitemclick(City details) {

                sendcity(details.getName());



            }



        });
        recyclerView.setAdapter(myAdapter);
        firebaseStorage = FirebaseStorage.getInstance().getReference().child("City/");
        firebaseStorage.listAll().addOnCompleteListener(task -> {
            List<StorageReference> tasks = task.getResult().getItems();
            for (int i = 0; i < tasks.size(); i++) {
                StorageReference reference = tasks.get(i);
                reference.getDownloadUrl().addOnCompleteListener(urlTask -> {

                    Uri imageUri = urlTask.getResult();
                    Log.e("--TAG", "onCreate: " + imageUri);
                    //   database.child(a.getName());
                    list.add(new City(reference.getName(), imageUri));

                    if (list.size() == tasks.size()) myAdapter.notifyItemRangeInserted(0,list.size());
                });
            }

        });


    }


    public void sendcity(String message){
        //Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(choosecity.this, chooseclinic.class);
        intent.putExtra("the city is ",message);
        startActivity(intent);

    }




    public void onBackPressed(){
        progressDialog.dismiss();

    }

    public void progress_Dailog(){
        progressDialog =new ProgressDialog(choosecity.this);
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