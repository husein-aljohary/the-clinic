package com.example.clinic.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clinic.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class choosedepartment extends AppCompatActivity {

    DatabaseReference reff ,reff2;
    Spinner spinner5,spinner6 ;
    String chosen_spinner5,chosen_spinner6;
    ArrayAdapter<String> adapter1,adapter2;
    ValueEventListener listener,listener2;
    ArrayList<String> list,list2;


    BottomNavigationView nav;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosedepartment);

        String cityname=getIntent().getStringExtra("city");
        String clinicname=getIntent().getStringExtra("clinic");
        //System.out.println("*** the dat is ***"+cityname+" "+clinicname);

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



        reff=FirebaseDatabase.getInstance().getReference().child("clinicnfo").child(cityname).child(clinicname).child("Doctors");
        spinner5=findViewById(R.id.spinner5);
        list=new ArrayList<>();
        adapter1=new ArrayAdapter<>(choosedepartment.this,android.R.layout.simple_spinner_item,list);
        spinner5.setAdapter(adapter1);
///***
        reff2=FirebaseDatabase.getInstance().getReference().child("clinicnfo").child(cityname).child(clinicname);
        //reff2=FirebaseDatabase.getInstance().getReference().child("clinicnfo").child(cityname).child(clinicname).child("Doctors").child(chosen_spinner5).child("apoitments");
        //reff2=FirebaseDatabase.getInstance().getReference().child("clinicnfo").child(cityname).child(clinicname).child("Doctors").child("Moshey").child("apoitments");
///***
        spinner6=findViewById(R.id.spinner6);
        list2=new ArrayList<>();
        adapter2=new ArrayAdapter<>(choosedepartment.this,android.R.layout.simple_spinner_item,list2);
        spinner6.setAdapter(adapter2);




        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosen_spinner5=adapterView.getItemAtPosition(i).toString();
                reff2=FirebaseDatabase.getInstance().getReference().child("clinicnfo").child(cityname).child(clinicname).child("Doctors").child(chosen_spinner5).child("apoitments");

                //fetchdata2();

                DatabaseReference dayOneRef = reff.child(cityname).child(clinicname).child("Doctors");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        /*for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            //numlist.add(String.valueOf(String.valueOf(ds)));
                        }*/
                        //numberpark.setText("Number of park in "+chosen_spinner3+" : "+dataSnapshot.getChildrenCount());


                        //list.add(String.valueOf(dataSnapshot));
                        //available_park.setText("The Available parks : "+trueth.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
                    }
                };
                dayOneRef.addListenerForSingleValueEvent(valueEventListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


/////////////////////////////////////////////////////////////////
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosen_spinner6=adapterView.getItemAtPosition(i).toString();
                //fetchdata2();

                //DatabaseReference dayOneRef = reff2.child(cityname).child(clinicname).child("departments");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        /*for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            //numlist.add(String.valueOf(String.valueOf(ds)));
                        }*/


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
                    }
                };
                //dayOneRef.addListenerForSingleValueEvent(valueEventListener);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
/////////////////////////////////////////////////////////////////

        fetchdataall();
        fetchdataall2();

    }


    public void fetchdataall(){
        listener=reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot mydata:snapshot.getChildren() ){
                    list.add(String.valueOf(String.valueOf(mydata.getKey())));
                }

                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /////////////////////////////////////////////////
    public void fetchdataall2(){
        listener2=reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                                                                         //chosen_spinner5
                                                                        //Moshey
                for(DataSnapshot mydata:snapshot.child("Doctors").child(chosen_spinner5).child("apoitments").getChildren() ){
                    list2.add(String.valueOf(String.valueOf(mydata.getKey())));
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    ////////////////////////////////////////////////////////////////////




}