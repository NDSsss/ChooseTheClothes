package com.example.nds.choosetheclothe.clothe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nds.choosetheclothe.App;
import com.example.nds.choosetheclothe.R;

import java.util.ArrayList;

public class ClotheAdapter extends RecyclerView.Adapter<ClotheAdapter.ViewHolder> {

    private ArrayList<Clothe> clothes;

    public ClotheAdapter(ArrayList<Clothe> clothes){
        this.clothes = clothes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_clothe,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.ivIcon.setImageDrawable(viewHolder.ivIcon.getContext().getResources().getDrawable(clothes.get(i).getResId()));
        viewHolder.tvName.setText(clothes.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivIcon;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_item_clothe);
            tvName = itemView.findViewById(R.id.tv_item_clothe);
        }
    }
}
