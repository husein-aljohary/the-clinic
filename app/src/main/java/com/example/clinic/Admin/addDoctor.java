package com.example.clinic.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class addDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    DatabaseReference reff ,dbref;
    Spinner spinner2, spinner3 ;
    String chosen_spinner,chosen_spinner3;






    /*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosen_spinner=adapterView.getItemAtPosition(i).toString();
        fetchdata();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter1;


    EditText depname,starttime,endtime;
    ImageView imageView;
    Button btnsave;
    private int img_request_id = 10;
    private Uri imgUri;
    AlertDialog.Builder Alert;
    FirebaseStorage storage1;
    StorageReference storageReferencedep;



/*


    DatabaseReference dbref;
    Spinner spinner3;
    String chosen_spinner3;
    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter1;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);


        BottomNavigationView bottomNavigationView=findViewById(R.id.navigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.adddoctor);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.Delete:
                        startActivity(new Intent(getApplicationContext(),Delete.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.Space:
                        startActivity(new Intent(getApplicationContext(), SpaceActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });




        reff= FirebaseDatabase.getInstance().getReference().child("clinicnfo");

        spinner2=findViewById(R.id.spinner2);
        //showing all the cities
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);

        dbref=FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        spinner3=findViewById(R.id.spinner3);
        list=new ArrayList<>();
        adapter1=new ArrayAdapter<>(addDoctor.this,android.R.layout.simple_spinner_item,list);
        spinner3.setAdapter(adapter1);




        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosen_spinner=adapterView.getItemAtPosition(i).toString();
                fetchdata();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        //showing the clinics in the city that we choose it
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chosen_spinner3=adapterView.getItemAtPosition(i).toString();
                //fetchdata2();

                DatabaseReference dayOneRef = reff.child(chosen_spinner).child(chosen_spinner3).child("departments");
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        /*for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            //numlist.add(String.valueOf(String.valueOf(ds)));
                        }*/
                        //numberpark.setText("Number of park in "+chosen_spinner3+" : "+dataSnapshot.getChildrenCount());


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

        depname = (EditText) findViewById(R.id.docname);
        starttime=(EditText) findViewById(R.id.starttime);
        endtime=(EditText) findViewById(R.id.endtime);
        imageView=(ImageView) findViewById(R.id.docimage);
        btnsave = (Button) findViewById(R.id.btnsave);
        storage1=FirebaseStorage.getInstance();
        storageReferencedep=storage1.getReference();//.child("Doctors/");
        imageView.setTag("png1");



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestImage();
            }
        });



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dep=depname.getText().toString().trim();

                if (dep.isEmpty()) {
                    depname.setError("Doctor is Empty");
                    depname.requestFocus();
                    return;
                }

                if (imageView.getTag()==("png1") ){
                    //Toast.makeText(cities.this, "Please choose photos", Toast.LENGTH_SHORT).show();
                    Alert=new AlertDialog.Builder(addDoctor.this);
                    Alert.setTitle("Can't add ! ");
                    Alert.setMessage("Please choose pictures of the Doctor");
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
                    Alart();
                }


            }
        });



        fetchdataall();


        ////////////
        /*
        reff = FirebaseDatabase.getInstance().getReference();
        storage1=FirebaseStorage.getInstance();
        storageReferencedep=storage1.getReference();//.child("Doctors/");
        imageView.setTag("png1");
        starttime=(EditText) findViewById(R.id.starttime);
        endtime=(EditText) findViewById(R.id.endtime);

        dbref=FirebaseDatabase.getInstance().getReference().child("Departmens");
        spinner3=findViewById(R.id.spinner);
        list=new ArrayList<>();
        adapter1=new ArrayAdapter<>(addDoctor.this,android.R.layout.simple_spinner_item,list);
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



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestImage();
            }
        });



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dep=depname.getText().toString().trim();

                if (dep.isEmpty()) {
                    depname.setError("Doctor is Empty");
                    depname.requestFocus();
                    return;
                }

                if (imageView.getTag()==("png1") ){
                    //Toast.makeText(cities.this, "Please choose photos", Toast.LENGTH_SHORT).show();
                    Alert=new AlertDialog.Builder(addDoctor.this);
                    Alert.setTitle("Can't add ! ");
                    Alert.setMessage("Please choose pictures of the Doctor");
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
                    Alart();
                }


            }
        });



        fetchdataall();
        */

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
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void fetchdataall(){
        listener=dbref.addValueEventListener(new ValueEventListener() {
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

    private void requestImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),img_request_id);
    }

    private void  Clear(){
        depname.getText().clear();
        imageView.setTag("png1");
        imageView.setImageResource(android.R.drawable.ic_menu_crop);
    }


    private void saveInFirebase() {
        if(imgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please waite...");
            progressDialog.show();
            StorageReference reference = storageReferencedep.child("Doctors/").child(chosen_spinner3).child(depname.getText().toString());

            reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(addDoctor.this, "Saved successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addDoctor.this, "ERROR... "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("saved" + (int) progress + "%");

                }
            });
        }
    }

    private void Alart(){

        Alert=new AlertDialog.Builder(addDoctor.this);
        Alert.setTitle("Welcome Admin ");
        Alert.setMessage("Are you sure to add "+depname.getText().toString());
        Alert.setCancelable(true);

        Alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upload();



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

    private  void upload(){

        saveInFirebase();
        Doctor d=new Doctor(depname.getText().toString(),chosen_spinner3,starttime.getText().toString(),endtime.getText().toString());
        //reff.child("Doctors").child(chosen_spinner3).child(depname.getText().toString()).setValue(d);
        reff.child(chosen_spinner).child(chosen_spinner3).child("Doctors").child(depname.getText().toString()).setValue(d);
        //reff.push().child("department").setValue(chosen_spinner3);
        Toast.makeText(addDoctor.this, "Successful Insert", Toast.LENGTH_SHORT).show();
        Clear();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == img_request_id && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmapimg = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bitmapimg);
                imageView.setTag("done1");
                // insertcityphoto.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /*
    public void fetchdataall(){
        listener=dbref.addValueEventListener(new ValueEventListener() {
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

    private void requestImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),img_request_id);
    }

    private void  Clear(){
        depname.getText().clear();
        imageView.setTag("png1");
        imageView.setImageResource(android.R.drawable.ic_menu_crop);
    }


    private void saveInFirebase() {
        if(imgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please waite...");
            progressDialog.show();
            StorageReference reference = storageReferencedep.child("Doctors/").child(chosen_spinner3).child(depname.getText().toString());

            reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(addDoctor.this, "Saved successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addDoctor.this, "ERROR... "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("saved" + (int) progress + "%");

                }
            });
        }
    }

    private void Alart(){

        Alert=new AlertDialog.Builder(addDoctor.this);
        Alert.setTitle("Welcome Admin ");
        Alert.setMessage("Are you sure to add "+depname.getText().toString());
        Alert.setCancelable(true);

        Alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upload();



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

    private  void upload(){

        saveInFirebase();
        Doctor d=new Doctor(depname.getText().toString(),chosen_spinner3,starttime.getText().toString(),endtime.getText().toString());
        reff.child("Departmens").child(chosen_spinner3).child(depname.getText().toString()).setValue(d);
        //reff.push().child("department").setValue(chosen_spinner3);
        Toast.makeText(addDoctor.this, "Successful Insert", Toast.LENGTH_SHORT).show();
        Clear();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == img_request_id && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            try {
                Bitmap bitmapimg = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bitmapimg);
                imageView.setTag("done1");
                // insertcityphoto.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */




}