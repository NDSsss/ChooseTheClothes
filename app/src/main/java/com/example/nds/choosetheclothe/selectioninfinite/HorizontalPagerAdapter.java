package com.example.nds.choosetheclothe.selectioninfinite;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.clothe.Clothe;

import java.util.ArrayList;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {

    private ArrayList<Clothe> clothes;

    public HorizontalPagerAdapter(ArrayList<Clothe> clothes) {
        this.clothes = clothes;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
            view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_clothe, container, false);
        ((ImageView)view.findViewById(R.id.iv_item_clothe)).setImageDrawable(container.getContext().getResources().getDrawable(clothes.get(position).getResId()));
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
}
