package com.example.clinic.Admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clinic.R;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    Context context;
    ArrayList<Clinic> mallArrayList;


    private int image;
    private Itemclicklistener sItemlistener;

    public MyAdapter2(Context context, ArrayList<Clinic> mallArrayList, Itemclicklistener itemclicklistener) {
        this.context = context;
        this.mallArrayList = mallArrayList;
        this.sItemlistener=itemclicklistener;


    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Clinic clinic= mallArrayList.get(position);

        Log.e("--TAG", "onBindViewHolder: " + clinic.getName() );
        Log.e("--TAG", "onBindViewHolder: " + clinic.getImageUrl() );
        holder.name.setText(clinic.getName());
        Glide.with(holder.image.getContext()).load(mallArrayList.get(position).getImageUrl()).into(holder.image);



        holder.itemView.setOnClickListener(view -> {
            sItemlistener.onitemclick(mallArrayList.get(position));
        });



    }

    @Override
    public int getItemCount() {
        return mallArrayList.size();
    }

    public interface Itemclicklistener{
        void onitemclick(Clinic details);


       // void onitemclick(Clinic details);
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView name;
        ImageView image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_mall);
            image=itemView.findViewById(R.id.image2);
        }
    }

}