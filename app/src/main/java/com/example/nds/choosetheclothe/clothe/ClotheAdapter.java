package com.example.nds.choosetheclothe.clothe;

import android.content.Context;
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
    private Context mContext;
    private ArrayList<Clothe> mDebitList;
    private HorizontalPagerAdapter.OnItemSelected onItemSelected;

    public ClotheAdapter(Context context, HorizontalPagerAdapter.OnItemSelected onItemSelected) {
        this.mContext = context;
        this.mDebitList = new ArrayList<>();
        this.onItemSelected = onItemSelected;
    }

    public void setData(ArrayList<Clothe> debitList) {
        this.mDebitList = debitList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_clothe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDebitList != null) {
            holder.ivIcon.setImageDrawable(mContext.getResources().getDrawable(mDebitList.get(position).getResId()));
            holder.tvName.setText(mDebitList.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return mDebitList == null ? 0 : mDebitList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivIcon;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_item_clothe);
            tvName = itemView.findViewById(R.id.tv_item_clothe);
            itemView.getLayoutParams().width = -2;
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            onItemSelected.itemSelected(mDebitList.get(getAdapterPosition()));
        }
    }
}
