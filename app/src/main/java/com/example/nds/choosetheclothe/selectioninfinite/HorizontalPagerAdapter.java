package com.example.nds.choosetheclothe.selectioninfinite;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.clothe.Clothe;

import java.util.ArrayList;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {
    public static final String TAG ="HorizontalPagerAdapter";

    private ArrayList<Clothe> clothes;
    private OnItemSelected mOnItemSelected;

    public HorizontalPagerAdapter(ArrayList<Clothe> clothes,OnItemSelected mOnItemSelected) {
        this.clothes = clothes;
        this.mOnItemSelected = mOnItemSelected;
    }

    @Override
    public int getCount() {
        return clothes==null?0:clothes.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Log.d(TAG, "instantiateItem: ");
        final View view;
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_clothe, container, false);
        ((ImageView)view.findViewById(R.id.iv_item_clothe)).setImageDrawable(container.getContext().getResources().getDrawable(clothes.get(position).getResId()));
        ((TextView)view.findViewById(R.id.tv_item_clothe)).setText(clothes.get(position).getName());
        ((TextView)view.findViewById(R.id.tv_item_clothe_raiting)).setText(String.valueOf(clothes.get(position).getRaiting()));
        ((TextView)view.findViewById(R.id.tv_item_clothe_min_temp)).setText(String.valueOf(clothes.get(position).getMinTemp()));
        ((TextView)view.findViewById(R.id.tv_item_clothe_max_temp)).setText(String.valueOf(clothes.get(position).getMaxTemp()));
        view.setOnClickListener(v->mOnItemSelected.itemSelected(clothes.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    public void setData(ArrayList<Clothe> clothes){
        this.clothes.clear();
        this.clothes.addAll(clothes);
        super.notifyDataSetChanged();
//        super.notifyAll();
        notifyDataSetChanged();
    }

    public void setItem(Clothe clothe){
        for (int i = 0; i <clothes.size();i++){
            if(clothes.get(i).getId() == clothe.getId()){
                clothes.set(i,clothe);
                super.notifyDataSetChanged();
                notifyDataSetChanged();
            }
        }
    }

    public interface OnItemSelected{
        void itemSelected(Clothe clothe);
    }
}
