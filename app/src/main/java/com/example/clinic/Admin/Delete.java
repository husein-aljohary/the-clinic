package com.example.clinic.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;

public class Delete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnDelete;
    DatabaseReference reff ,dbref;
    //**/*/*/*/*/*/*/*/*/*/*/
    Spinner spinner2;
    String chosen_spinner;
    ////****/*//*/*/*/**//*/*/////
    FirebaseStorage storage1;
    StorageReference storageReferenceCity;
    //////////////////////////////////////////////////////////////
    Spinner spinner3;
    String chosen_spinner3;
    AlertDialog.Builder Alert;


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosen_spinner=adapterView.getItemAtPosition(i).toString();
        fetchdata();
        //Toast.makeText(adapterView.getContext(),chosen_spinner,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //////////////////////////////////////////////////////////////////////
    ValueEventListener listener;
    ArrayList<String>list;
    ArrayAdapter<String>adapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.Delete);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Delete:
                        return true;
                    case R.id.Space:
                        startActivity(new Intent(getApplicationContext(), SpaceActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });




        reff=FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        btnDelete=findViewById(R.id.BtnDelete);
        spinner2=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);
////////////////////////////////////////////////////////////////////////////////////
        dbref=FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        spinner3=findViewById(R.id.spinner3);
        list=new ArrayList<>();
        adapter1=new ArrayAdapter<>(Delete.this,android.R.layout.simple_spinner_item,list);
        spinner3.setAdapter(adapter1);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosen_spinner3=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



///*//////////////////****
        storage1=FirebaseStorage.getInstance();
        storageReferenceCity=storage1.getReference().child("City/");




        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chosen_spinner3==null){

                 //   Toast.makeText(Delete.this, "please select mall", Toast.LENGTH_SHORT).show();
                    Alert=new AlertDialog.Builder(Delete.this);
                    Alert.setTitle("Can't Delete the clinic ! ");
                    Alert.setMessage("please select clinic delete.");
                    Alert.setCancelable(true);
                    Alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog=Alert.create();
                    dialog.show();

                }

                else{
                reff.child(chosen_spinner).child(chosen_spinner3).removeValue();
                ///************delete th image from the storage
                storageReferenceCity.child(chosen_spinner).child(chosen_spinner3).delete();
                Toast.makeText(Delete.this, " Done Delete "+chosen_spinner3, Toast.LENGTH_SHORT).show();
                //}

            }}
        });




    }

    public void fetchdata(){
        listener=dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot mydata:snapshot.child(chosen_spinner).getChildren() ){
                    list.add(String.valueOf(String.valueOf(mydata.getKey())));
                }

                adapter1.notifyDataSetChanged();
            }
                        /*
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata:snapshot.child("clinicnfo").child("Amman").getChildren() )
                    list.add(String.valueOf(String.valueOf(mydata.getKey())));
                adapter1.notifyDataSetChanged();
            }

             */

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






}
