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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<City> cityArrayList;



    private int image;
    private Itemclicklistener mItemlistener;

    public MyAdapter(Context context, ArrayList<City> cityArrayList, Itemclicklistener itemclicklistener) {
        this.context = context;
        this.cityArrayList = cityArrayList;
        //new

        this.mItemlistener=itemclicklistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        City city= cityArrayList.get(position);

        Log.e("--TAG", "onBindViewHolder: " + city.getName() );
        Log.e("--TAG", "onBindViewHolder: " + city.getImageUrl() );
        holder.name.setText(city.getName());
        Glide.with(holder.image.getContext()).load(cityArrayList.get(position).getImageUrl()).into(holder.image);


        //new mall







        holder.itemView.setOnClickListener(view -> {
            mItemlistener.onitemclick(cityArrayList.get(position));
        });



    }

    @Override
    public int getItemCount() {
        return cityArrayList.size();
    }

    public interface Itemclicklistener{
        void onitemclick(City details);


    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView name ,tvonline;
        ImageView image;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvname);
            image=itemView.findViewById(R.id.image1);
            //tvonline=itemView.findViewById(R.id.tvonline);


        }
    }


}