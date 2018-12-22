package com.example.nds.choosetheclothe.selectioninfinite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.base.BaseLoadingFragment;
import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.eventbus.Event;
import com.example.nds.choosetheclothe.eventbus.events.UpdateTempEvent;
import com.example.nds.choosetheclothe.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;

public class SelectionInfiniteFragment extends BaseLoadingFragment {
    public static final int TSHIRT = 0;
    public static final int PANS = 1;
    public static final int SHOES = 2;

    private static final String TAG = "SelectionInfinite ";
    ArrayList<Clothe> listTshirt, listPans, listShoes;
    HorizontalPagerAdapter adapterTshirt, adapterPans, adapterShoes;
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPagerTshirt, horizontalInfiniteCycleViewPagerPans, horizontalInfiniteCycleViewPagerShoes;
    TextView tvThirtEmpty, tvPansEmpty, tvShoesEmpty;
    private double mTemp = -1000d;
    private boolean isEnabled = false;

    public void initData() {
        listTshirt = new ArrayList<>();
        listTshirt.add(new Clothe(10, 20, 5, R.drawable.thirt_midle, "tMiddle"));
        listTshirt.add(new Clothe(15, 30, 5, R.drawable.thirt_cold, "tCold"));
        listTshirt.add(new Clothe(-15, 10, 5, R.drawable.thirt_warm, "tWarm"));
        listPans = new ArrayList<>();
        listPans.add(new Clothe(10, 20, 5, R.drawable.pans_midle, "pMiddle"));
        listPans.add(new Clothe(15, 30, 5, R.drawable.pans_cold, "pCold"));
        listPans.add(new Clothe(-15, 10, 5, R.drawable.pans_warm, "pWarm"));
        listShoes = new ArrayList<>();
        listShoes.add(new Clothe(10, 20, 5, R.drawable.shoes_midle, "sMiddle"));
        listShoes.add(new Clothe(15, 30, 5, R.drawable.shoes_cold, "sCold"));
        listShoes.add(new Clothe(-15, 10, 5, R.drawable.shoes_warm, "sWarm"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection_infinite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvThirtEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_tshirt_empty);
        tvPansEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_pans_empty);
        tvShoesEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_shoes_empty);
        horizontalInfiniteCycleViewPagerTshirt =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp_infinite_scrolling_tshirt);
        adapterTshirt = new HorizontalPagerAdapter(listTshirt);
        horizontalInfiniteCycleViewPagerTshirt.setAdapter(adapterTshirt);
        horizontalInfiniteCycleViewPagerPans =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp_infinite_scrolling_pans);
        adapterPans = new HorizontalPagerAdapter(listPans);
        horizontalInfiniteCycleViewPagerPans.setAdapter(adapterPans);
        horizontalInfiniteCycleViewPagerShoes =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp_infinite_scrolling_shoes);
        adapterShoes = new HorizontalPagerAdapter(listShoes);
        horizontalInfiniteCycleViewPagerShoes.setAdapter(adapterShoes);
    }

    @Override
    public void onResume() {
        super.onResume();
//        filtreClothe(mTemp);
        mLoadingListener.completeLoading();
        isEnabled = true;
    }


    private void filtreClothe(double temp) {
        mTemp = temp;
        if(isEnabled) {
            ArrayList<Clothe> filtredClothes = new ArrayList<>();
            for (int i = 0; i < listTshirt.size(); i++) {
                if (listTshirt.get(i).getMinTemp() < temp - 10 && listTshirt.get(i).getMaxTemp() > temp + 10) {
                    filtredClothes.add(listTshirt.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                adapterTshirt.setData(filtredClothes);

                adapterTshirt = new HorizontalPagerAdapter(filtredClothes);
                horizontalInfiniteCycleViewPagerTshirt.invalidate();
                horizontalInfiniteCycleViewPagerTshirt.setAdapter(adapterTshirt);
            } else {
                Log.d(TAG, "filtreClothe: no tshirts found");
                showEmptyError(TSHIRT, "No thirts");
            }
            filtredClothes.clear();
            for (int i = 0; i < listPans.size(); i++) {
                if (listPans.get(i).getMinTemp() < temp - 10 && listPans.get(i).getMaxTemp() > temp + 10) {
                    filtredClothes.add(listPans.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                adapterPans.setData(filtredClothes);
                adapterPans = new HorizontalPagerAdapter(filtredClothes);
                horizontalInfiniteCycleViewPagerPans.invalidate();
                horizontalInfiniteCycleViewPagerPans.setAdapter(adapterPans);
            } else {
                Log.d(TAG, "filtreClothe: no pans found");
                showEmptyError(PANS, "No pans");
            }
            filtredClothes.clear();
            for (int i = 0; i < listShoes.size(); i++) {
                if (listShoes.get(i).getMinTemp() < temp - 10 && listShoes.get(i).getMaxTemp() > temp + 10) {
                    filtredClothes.add(listShoes.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                adapterShoes.setData(filtredClothes);
                adapterShoes = new HorizontalPagerAdapter(filtredClothes);
                horizontalInfiniteCycleViewPagerShoes.invalidate();
                horizontalInfiniteCycleViewPagerShoes.setAdapter(adapterShoes);
            } else {
                Log.d(TAG, "filtreClothe: no shoes found");
                showEmptyError(SHOES, "No shoes");
            }
            filtredClothes.clear();
        }
    }

    private void showEmptyError(int type, String errorMessage) {
        if (tvPansEmpty != null) {
            switch (type) {
                case TSHIRT:
                    horizontalInfiniteCycleViewPagerTshirt.setVisibility(View.GONE);
                    tvThirtEmpty.setVisibility(View.VISIBLE);
                    tvThirtEmpty.setText(errorMessage);
                    break;
                case PANS:
                    horizontalInfiniteCycleViewPagerPans.setVisibility(View.GONE);
                    tvPansEmpty.setVisibility(View.VISIBLE);
                    tvPansEmpty.setText(errorMessage);
                    break;
                case SHOES:
                    horizontalInfiniteCycleViewPagerShoes.setVisibility(View.GONE);
                    tvShoesEmpty.setVisibility(View.VISIBLE);
                    tvShoesEmpty.setText(errorMessage);
                    break;
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateTempEvent) {
            filtreClothe(((UpdateTempEvent) event).getNewTemp());
        }
    }
}
