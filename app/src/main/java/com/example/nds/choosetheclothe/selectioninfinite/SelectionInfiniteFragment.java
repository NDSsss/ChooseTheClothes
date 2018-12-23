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
import com.example.nds.choosetheclothe.clothe.ChangeClotheParamsDialog;
import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.eventbus.Event;
import com.example.nds.choosetheclothe.eventbus.events.SettingClotheEvent;
import com.example.nds.choosetheclothe.eventbus.events.UpdateClotheEvent;
import com.example.nds.choosetheclothe.eventbus.events.UpdateTempEvent;
import com.example.nds.choosetheclothe.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class SelectionInfiniteFragment extends BaseLoadingFragment implements HorizontalPagerAdapter.OnItemSelected, ChangeClotheParamsDialog.SaveClothe {
    public static final int TSHIRT = 0;
    public static final int PANS = 1;
    public static final int SHOES = 2;

    private static final String TAG = "SelectionInfinite ";
    ArrayList<Clothe> listTshirt, listPans, listShoes;
    HorizontalPagerAdapter adapterTshirt, adapterPans, adapterShoes;
    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPagerTshirt, horizontalInfiniteCycleViewPagerPans, horizontalInfiniteCycleViewPagerShoes;
    TextView tvThirtEmpty, tvPansEmpty, tvShoesEmpty;
    private double mTemp = -1000d;
    private double minTemp = -1000d;
    private double maxTemp = -1000d;
    private boolean isEnabled = false;

    public void initData(List<Clothe> clothes) {
        if (clothes != null) {
            listTshirt = new ArrayList<>();
            listPans = new ArrayList<>();
            listShoes = new ArrayList<>();
            for (int i = 0; i < clothes.size(); i++) {
                switch (clothes.get(i).getType()) {
                    case Clothe.TYPE_THIRT:
                        listTshirt.add(clothes.get(i));
                        break;
                    case Clothe.TYPE_PANS:
                        listPans.add(clothes.get(i));
                        break;
                    case Clothe.TYPE_SHOES:
                        listShoes.add(clothes.get(i));
                        break;
                }
            }
            filtreClothe(0, 0);
        } else {

            listTshirt = new ArrayList<>();
            listTshirt.add(new Clothe(10, 20, 5, R.drawable.thirt_midle, "tMiddle", Clothe.TYPE_THIRT));
            listTshirt.add(new Clothe(15, 30, 5, R.drawable.thirt_cold, "tCold", Clothe.TYPE_THIRT));
            listTshirt.add(new Clothe(-15, 10, 5, R.drawable.thirt_warm, "tWarm", Clothe.TYPE_THIRT));
            listPans = new ArrayList<>();
            listPans.add(new Clothe(10, 20, 5, R.drawable.pans_midle, "pMiddle", Clothe.TYPE_PANS));
            listPans.add(new Clothe(15, 30, 5, R.drawable.pans_cold, "pCold", Clothe.TYPE_PANS));
            listPans.add(new Clothe(-15, 10, 5, R.drawable.pans_warm, "pWarm", Clothe.TYPE_PANS));
            listShoes = new ArrayList<>();
            listShoes.add(new Clothe(10, 20, 5, R.drawable.shoes_midle, "sMiddle", Clothe.TYPE_SHOES));
            listShoes.add(new Clothe(15, 30, 5, R.drawable.shoes_cold, "sCold", Clothe.TYPE_SHOES));
            listShoes.add(new Clothe(-15, 10, 5, R.drawable.shoes_warm, "sWarm", Clothe.TYPE_SHOES));
        }
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
        adapterTshirt = new HorizontalPagerAdapter(listTshirt, this::itemSelected);
        horizontalInfiniteCycleViewPagerTshirt.setAdapter(adapterTshirt);
        horizontalInfiniteCycleViewPagerPans =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp_infinite_scrolling_pans);
        adapterPans = new HorizontalPagerAdapter(listPans, this::itemSelected);
        horizontalInfiniteCycleViewPagerPans.setAdapter(adapterPans);
        horizontalInfiniteCycleViewPagerShoes =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp_infinite_scrolling_shoes);
        adapterShoes = new HorizontalPagerAdapter(listShoes, this::itemSelected);
        horizontalInfiniteCycleViewPagerShoes.setAdapter(adapterShoes);
    }

    @Override
    public void onResume() {
        super.onResume();
//        filtreClothe(mTemp);
        mLoadingListener.completeLoading();
        isEnabled = true;
    }


    private void filtreClothe(double minTemp, double maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        if (isEnabled) {
            ArrayList<Clothe> filtredClothes = new ArrayList<>();
            for (int i = 0; i < listTshirt.size(); i++) {
                if (listTshirt.get(i).getMinTemp() < minTemp && listTshirt.get(i).getMaxTemp() > maxTemp) {
                    filtredClothes.add(listTshirt.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                listTshirt = filtredClothes;
                horizontalInfiniteCycleViewPagerTshirt.invalidate();
                horizontalInfiniteCycleViewPagerTshirt.setAdapter(new HorizontalPagerAdapter(filtredClothes, this::itemSelected));
            } else {
                Log.d(TAG, "filtreClothe: no tshirts found");
                showEmptyError(TSHIRT, "No thirts");
            }
            filtredClothes = new ArrayList<>();
            for (int i = 0; i < listPans.size(); i++) {
                if (listPans.get(i).getMinTemp() < minTemp && listPans.get(i).getMaxTemp() > maxTemp) {
                    filtredClothes.add(listPans.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                listPans = filtredClothes;
                horizontalInfiniteCycleViewPagerPans.invalidate();
                horizontalInfiniteCycleViewPagerPans.setAdapter(new HorizontalPagerAdapter(filtredClothes, this::itemSelected));
            } else {
                Log.d(TAG, "filtreClothe: no pans found");
                showEmptyError(PANS, "No pans");
            }
            filtredClothes = new ArrayList<>();
            for (int i = 0; i < listShoes.size(); i++) {
                if (listShoes.get(i).getMinTemp() < minTemp && listShoes.get(i).getMaxTemp() > maxTemp) {
                    filtredClothes.add(listShoes.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                listShoes = filtredClothes;
                horizontalInfiniteCycleViewPagerShoes.invalidate();
                horizontalInfiniteCycleViewPagerShoes.setAdapter(new HorizontalPagerAdapter(filtredClothes, this::itemSelected));
            } else {
                Log.d(TAG, "filtreClothe: no shoes found");
                showEmptyError(SHOES, "No shoes");
            }
            filtredClothes = new ArrayList<>();
        }
    }

    private void updateClothe(Clothe clothe) {
        switch (clothe.getType()) {
            case Clothe.TYPE_THIRT:
                Log.d(TAG, "updateClothe: TISHIRT");
                for (int i = 0; i < listTshirt.size(); i++) {
                    if (listTshirt.get(i).getId() == clothe.getId()) {
                        listTshirt.set(i, clothe);
                        horizontalInfiniteCycleViewPagerTshirt.setAdapter(new HorizontalPagerAdapter(listTshirt, this::itemSelected));
                    }
                }
                break;
            case Clothe.TYPE_PANS:
                Log.d(TAG, "updateClothe: PANS");
                for (int i = 0; i < listPans.size(); i++) {
                    if (listPans.get(i).getId() == clothe.getId()) {
                        listPans.set(i, clothe);
                        horizontalInfiniteCycleViewPagerPans.setAdapter(new HorizontalPagerAdapter(listPans, this::itemSelected));
                    }
                }
                break;
            case Clothe.TYPE_SHOES:
                Log.d(TAG, "updateClothe: SHOES");
                for (int i = 0; i < listShoes.size(); i++) {
                    if (listShoes.get(i).getId() == clothe.getId()) {
                        listShoes.set(i, clothe);
                        horizontalInfiniteCycleViewPagerShoes.setAdapter(new HorizontalPagerAdapter(listShoes, this::itemSelected));
                    }
                }
                break;
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
            mTemp = ((UpdateTempEvent) event).getNewTemp();
//            filtreClothe(mTemp,mTemp);
        } else if (event instanceof SettingClotheEvent) {
            initData((((SettingClotheEvent) event).getClothes()));
        }
    }

    @Override
    public void itemSelected(Clothe clothe) {
        ChangeClotheParamsDialog dialog = ChangeClotheParamsDialog.newInstance(clothe);
        dialog.setSaveClothe(this::saveClothe);
        dialog.show(getActivity().getSupportFragmentManager(), ChangeClotheParamsDialog.TAG);
    }

    @Override
    public void saveClothe(Clothe clothe) {
        Log.d(TAG, "saveClothe: ");
        updateClothe(clothe);
        getEventBus.getEventBus().notifyEvent(new UpdateClotheEvent(clothe));
    }
}
