package com.example.clinic.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinic.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class home extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText tecity,teclinicname,tedepartmentsnumber,num;
    Button btnsave ,adddoctor;
    DatabaseReference reff;
    clinicnfo clinicnfo1;
    parkingspace sp;
    TextView Delete;
    EditText tenumber;
    AlertDialog.Builder Alert;
    int departmentsnumber=0;





    //image view city&mall
    Button insertcityphoto,insertmallphoto;
    ImageView imageView,imageView2;
    FirebaseStorage storage1,storage2;
    StorageReference storageReferenceCity,storageReferenceMall ;
    private int img_request_id = 10;
    private Uri imgUri;
    private int img_request_id2 = 20;
    private Uri imgUri2;
    Spinner spinner;
    String chosen_spinner;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosen_spinner=adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(),chosen_spinner,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView=findViewById(R.id.navigationView);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

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

        teclinicname = (EditText) findViewById(R.id.temall);
        //tecity = (EditText) findViewById(R.id.EmailRegester);
        tedepartmentsnumber = (EditText) findViewById(R.id.teparksnumber);
        btnsave = (Button) findViewById(R.id.btnsave);
        adddoctor = (Button) findViewById(R.id.adddoctor);
        spinner=(Spinner) findViewById(R.id.spinner);
        reff = FirebaseDatabase.getInstance().getReference().child("clinicnfo");
        clinicnfo1 = new clinicnfo();
        sp = new parkingspace();

        ArrayList<parkingspace> mylist = new ArrayList<parkingspace>();
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        imageView=(ImageView) findViewById(R.id.imageView);
        imageView2=(ImageView) findViewById(R.id.imageView2);
        storage1=FirebaseStorage.getInstance();
        storage2=FirebaseStorage.getInstance();
        storageReferenceCity=storage1.getReference().child("City/");




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestImage();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestImage2();
            }
        });




        adddoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),addDoctor.class));
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clinicnfo1.setCity(chosen_spinner.trim());
                clinicnfo1.setclinicname(teclinicname.getText().toString().trim());
                clinicnfo1.setdepartmentsnumber(tedepartmentsnumber.getText().toString().trim());
                String Mall =teclinicname.getText().toString().trim();
                String parknum =tedepartmentsnumber.getText().toString().trim();

                if (Mall.isEmpty()) {
                    teclinicname.setError("Clinic name is Empty");
                    teclinicname.requestFocus();
                    return;
                }
                /*
                if (parknum.isEmpty()) {
                    tedepartmentsnumber.setError("Departments number is Empty");
                    tedepartmentsnumber.requestFocus();
                    return;

                }*/

                if (imageView.getTag().equals("png1") || imageView2.getTag().equals("png2")){
                    Alert=new AlertDialog.Builder(home.this);
                    Alert.setTitle("Can't add Image ! ");
                    Alert.setMessage("Please choose pictures of the city and the Clinic");
                    Alert.setCancelable(true);
                    Alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog=Alert.create();
                    dialog.show();

                }
                else {
                    if ( chosen_spinner.equalsIgnoreCase("Yerushalayim")) {
                        storageReferenceMall = storage2.getReference().child("City/").child("Yerushalayim/");//1
                        Alart();
                    } else if (chosen_spinner.equalsIgnoreCase("HaTzafon")) {
                        storageReferenceMall = storage2.getReference().child("City/").child("HaTzafon/");//2
                        Alart();
                    } else if (chosen_spinner.equalsIgnoreCase("Hifa")) {
                        storageReferenceMall = storage2.getReference().child("City/").child("Hifa/");//4
                        Alart();
                    }else if (chosen_spinner.equalsIgnoreCase("HaMerkaz")) {
                        storageReferenceMall = storage2.getReference().child("City/").child("HaMerkaz/");//5
                        Alart();
                    }else if (chosen_spinner.equalsIgnoreCase("TelAviv")) {
                        storageReferenceMall = storage2.getReference().child("City/").child("TelAviv/");//6
                        Alart();
                    }else if (chosen_spinner.equalsIgnoreCase("HaDarom") ) {
                        storageReferenceMall = storage2.getReference().child("City/").child("HaDarom/");//7
                        Alart();
                    }else if (chosen_spinner.equalsIgnoreCase("YehudaVeShomron")) {
                        storageReferenceMall = storage2.getReference().child("City/").child("YehudaVeShomron/");//8
                        Alart();
                    }
                    else {
                        Toast.makeText(home.this, "Please enter a valid city", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });


    }

    private void saveInFirebase2() {
        if(imgUri2 != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please waite...");
            progressDialog.show();
            StorageReference reference = storageReferenceMall.child(teclinicname.getText().toString()/*+ UUID.randomUUID().toString()*/);
           // reference.child("City/");
            reference.putFile(imgUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(home.this, "Saved successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(home.this, "ERROR... "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("saved  " + (int) progress + "%");

                }
            });
        }
    }


    private void requestImage2() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select picture"),img_request_id2);
        imageView2.setTag("done2");
    }

    private void requestImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),img_request_id);
        imageView.setTag("done");

    }
    //insert city photo
    private void Alart(){

        Alert=new AlertDialog.Builder(home.this);
        Alert.setTitle("Welcome Admin ");
        Alert.setMessage("Are you sure to add "+teclinicname.getText().toString()+" in the city "+chosen_spinner);
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

        saveInFirebase2();
        saveInFirebase();
        reff.child(chosen_spinner).child(teclinicname.getText().toString()).push().setValue(clinicnfo1);
        //reff.child(chosen_spinner).child(teclinicname.getText().toString()).child("departments").child(depname.getText().toString()).push().setValue(depname.getText().toString());

        //CreateSpace();
        Toast.makeText(home.this, "Successful Insert", Toast.LENGTH_SHORT).show();
       Clear();
    }
    /*
    private void CreateSpace(){
        departmentsnumber= Integer.parseInt(tedepartmentsnumber.getText().toString().trim());
        List<parkingspace> ll=new ArrayList<>();
        for (int i=0;i<departmentsnumber;i++){
            sp = new parkingspace();
            sp.setId("department " + i);
            ll.add(sp);
        }
        reff.child(chosen_spinner).child(teclinicname.getText().toString()).child("departments").setValue(ll);
    }*/

    private void  Clear(){
        teclinicname.getText().clear();
        tedepartmentsnumber.getText().clear();
        imageView.setImageResource(android.R.drawable.ic_menu_crop);
        imageView2.setImageResource(android.R.drawable.ic_menu_crop);
    }

    private void saveInFirebase() {
        if(imgUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please waite...");
            progressDialog.show();
            StorageReference reference = storageReferenceCity.child(chosen_spinner/*+ UUID.randomUUID().toString()*/);

            reference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(home.this, "Saved successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(home.this, "ERROR... "+e.getMessage(), Toast.LENGTH_SHORT).show();
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == img_request_id && resultCode == RESULT_OK && data != null && data.getData() !=null){
            imgUri=data.getData();
            try {
                Bitmap bitmapimg=MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imageView.setImageBitmap(bitmapimg);
               // insertcityphoto.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == img_request_id2 && resultCode == RESULT_OK && data != null && data.getData() !=null){
            imgUri2=data.getData();
            try {
                Bitmap bitmapimg2=MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri2);
                imageView2.setImageBitmap(bitmapimg2);
               // insertmallphoto.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }






}











