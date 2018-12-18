package com.example.nds.choosetheclothe.selectioninfinite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.base.BaseLoadingFragment;
import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;

public class SelectionInfiniteFragment extends BaseLoadingFragment {

    ArrayList<Clothe> listTshirt, listPans, listShoes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection_infinite,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLists();
        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(listTshirt));
        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager2 =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp2);
        horizontalInfiniteCycleViewPager2.setAdapter(new HorizontalPagerAdapter(listPans));
        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager3 =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp3);
        horizontalInfiniteCycleViewPager3.setAdapter(new HorizontalPagerAdapter(listShoes));
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadingListener.completeLoading();
    }

    private void initLists() {
        listTshirt = new ArrayList<>();
        listTshirt.add(new Clothe(-3, 15, 5, R.drawable.thirt_warm, "tWarm"));
        listTshirt.add(new Clothe(13, 20, 5, R.drawable.thirt_midle, "tMiddle"));
        listTshirt.add(new Clothe(17, 30, 5, R.drawable.thirt_cold, "tCold"));
        listPans = new ArrayList<>();
        listPans.add(new Clothe(-3, 15, 5, R.drawable.pans_warm, "pWarm"));
        listPans.add(new Clothe(13, 20, 5, R.drawable.pans_midle, "pMiddle"));
        listPans.add(new Clothe(17, 30, 5, R.drawable.pans_cold, "pCold"));
        listShoes = new ArrayList<>();
        listShoes.add(new Clothe(-3, 15, 5, R.drawable.shoes_warm, "sWarm"));
        listShoes.add(new Clothe(13, 20, 5, R.drawable.shoes_midle, "sMiddle"));
        listShoes.add(new Clothe(17, 30, 5, R.drawable.shoes_cold, "sCold"));
    }
}
