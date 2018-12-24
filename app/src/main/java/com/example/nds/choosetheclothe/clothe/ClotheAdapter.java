package com.example.nds.choosetheclothe.clothe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.selectioninfinite.HorizontalPagerAdapter;

import java.util.ArrayList;

public class ClotheAdapter extends RecyclerView.Adapter<ClotheAdapter.ViewHolder> {

    private ArrayList<Clothe> clothes;
    private HorizontalPagerAdapter.OnItemSelected onItemSelected;

    public ClotheAdapter(ArrayList<Clothe> clothes, HorizontalPagerAdapter.OnItemSelected onItemSelected){
        this.clothes = clothes;
        this.onItemSelected = onItemSelected;
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

    public void setData(ArrayList<Clothe> clothes){
        this.clothes = clothes;
        notifyDataSetChanged();
    }

    public void setItem(Clothe clothe){
        for(int i = 0; i < clothes.size(); i++){
            if(clothes.get(i).getId()==clothe.getId()){
                clothes.remove(i);
                clothes.add(i,clothe);
                notifyDataSetChanged();
            }
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemSelected.itemSelected(clothes.get(getAdapterPosition()));
                }
            });
        }
    }
}
