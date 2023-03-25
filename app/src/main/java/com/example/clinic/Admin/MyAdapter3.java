package com.example.clinic.Admin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clinic.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder> {

    private DatabaseReference database= FirebaseDatabase.getInstance().getReference();


    static Context context;
    ArrayList<Department> spaceArrayList;
    Intent intent;
    // private Itemclicklistener xItemlistener;
    public MyAdapter3(Context context, ArrayList<Department> spaceArrayList) {
        this.context = context;
        this.spaceArrayList = spaceArrayList;
        // this.xItemlistener=xItemlistener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item3,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return spaceArrayList.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView number , NumberSpace ,status,ss ;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            number=itemView.findViewById(R.id.text_space);
            //status=itemView.findViewById(R.id.avalbvle);

        }
    }
}