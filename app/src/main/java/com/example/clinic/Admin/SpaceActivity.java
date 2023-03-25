package com.example.clinic.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SpaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button btnDelete,deletespace;
    DatabaseReference reff ,dbref;
    String chosen_spinner;
    Spinner spinner2, spinner3 ,spinner4;
    String chosen_spinner3, chosen_spinner4;
    TextView numberpark ,time_date ,available_park;
    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    Button Editspace;
    ArrayList<String> trueth=new ArrayList<String>();
    ArrayList<String> numlist=new ArrayList<String>();
    AlertDialog.Builder Alert;
    EditText depname;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosen_spinner=adapterView.getItemAtPosition(i).toString();
        fetchdata();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter1;

    ArrayAdapter<String> adapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

        depname = (EditText) findViewById(R.id.depname);
        time_date=findViewById(R.id.time_date);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("EEEE-LLLL-yyyy  KK:mm:ss aaa ");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        time_date.setText(dateTime);






        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.Space);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @ Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.Delete:
                        startActivity(new Intent(getApplicationContext(), Delete.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Space:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), home.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        reff= FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        deletespace=findViewById(R.id.deletespace);
        btnDelete=findViewById(R.id.BtnDelete);

        Editspace=findViewById(R.id.Editspace);






        spinner2=findViewById(R.id.spinner2);
        //showing all the cities
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);

        dbref=FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        spinner3=findViewById(R.id.spinner3);
        list=new ArrayList<>();
        adapter1=new ArrayAdapter<>(SpaceActivity.this,android.R.layout.simple_spinner_item,list);
        spinner3.setAdapter(adapter1);
        numberpark = (TextView) findViewById(R.id.numberpark);
        //available_park= (TextView) findViewById(R.id.available_park);

        spinner4=findViewById(R.id.spinner4);
        adapter4=new ArrayAdapter<>(SpaceActivity.this,android.R.layout.simple_spinner_item,numlist);
        spinner4.setAdapter(adapter4);



        //showing the clinics in the city that we choose it
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosen_spinner3=adapterView.getItemAtPosition(i).toString();
                fetchdata2();

                DatabaseReference dayOneRef = reff.child(chosen_spinner).child(chosen_spinner3).child("departments");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        /*for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            //numlist.add(String.valueOf(String.valueOf(ds)));
                        }*/
                        numberpark.setText("Number of park in "+chosen_spinner3+" : "+dataSnapshot.getChildrenCount());
                        adapter4.notifyDataSetChanged();

                        //choose the department
                        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                chosen_spinner4=adapterView.getItemAtPosition(i).toString();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        //available_park.setText("The Available parks : "+trueth.size());
                        trueth.clear();
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


        //edite the parks availability
        Editspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert=new AlertDialog.Builder(SpaceActivity.this);
                Alert.setTitle("Welcome Admin ");
                Alert.setMessage("Are you sure to add the department ");
                Alert.setCancelable(true);

                Alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reff.child(chosen_spinner).child(chosen_spinner3).child("Doctors").child(chosen_spinner4).child("apoitments").child(depname.getText().toString()).push().setValue(depname.getText().toString());
                    }
                });
                Alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog=Alert.create();
                dialog.show();
            }
        });


        //delete department
        deletespace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert=new AlertDialog.Builder(SpaceActivity.this);
                Alert.setTitle("Welcome Admin ");
                Alert.setMessage("Are you sure to delete this department? ");
                Alert.setCancelable(true);

                Alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reff.child(chosen_spinner).child(chosen_spinner3).child("departments").child(chosen_spinner4).removeValue();

                        adapter4.notifyDataSetChanged();
                        Toast.makeText(SpaceActivity.this, "The "+chosen_spinner4+" department has been deleted  successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                Alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog dialog=Alert.create();
                dialog.show();
            }
        });





    }



    //display the data from database
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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fetchdata2(){
        listener=dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numlist.clear();
                for(DataSnapshot mydata:snapshot.child(chosen_spinner).child(chosen_spinner3).child("Doctors").getChildren() ){
                    numlist.add(String.valueOf(String.valueOf(mydata.getKey())));
                }
                adapter4.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }








}