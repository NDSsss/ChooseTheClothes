package com.example.nds.choosetheclothe.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nds.choosetheclothe.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TshirtAdaper extends RecyclerView.Adapter<TshirtAdaper.ViewHolder> {

    private ArrayList<Bitmap> images;

    public TshirtAdaper(ArrayList<Bitmap> images){
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.clothe_adapter_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.img.setImageBitmap(images.get(i));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setData(ArrayList<Bitmap> images){
        this.images = images;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.iw_clothe_adapter_item);
        }
    }
}
