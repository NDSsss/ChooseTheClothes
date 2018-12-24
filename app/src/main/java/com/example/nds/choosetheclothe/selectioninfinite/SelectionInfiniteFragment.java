package com.example.nds.choosetheclothe.selectioninfinite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.base.BaseLoadingFragment;
import com.example.nds.choosetheclothe.base.InfiniteScrollAdapter;
import com.example.nds.choosetheclothe.base.Orientation;
import com.example.nds.choosetheclothe.base.PageScrollView;
import com.example.nds.choosetheclothe.base.ScaleTransformer;
import com.example.nds.choosetheclothe.clothe.ChangeClotheParamsDialog;
import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.clothe.ClotheAdapter;
import com.example.nds.choosetheclothe.eventbus.Event;
import com.example.nds.choosetheclothe.eventbus.events.SettingClotheEvent;
import com.example.nds.choosetheclothe.eventbus.events.UpdateClotheEvent;
import com.example.nds.choosetheclothe.eventbus.events.UpdateTempEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionInfiniteFragment extends BaseLoadingFragment implements HorizontalPagerAdapter.OnItemSelected, ChangeClotheParamsDialog.SaveClothe {
    public static final int TSHIRT = 0;
    public static final int PANS = 1;
    public static final int SHOES = 2;
    public static final String SELECTION_INFINITE_FRAGMENT_RAWR_CLOTHES = "SELECTION_INFINITE_FRAGMENT_RAWR_CLOTHES";

    private static final String TAG = "SelectionInfinite ";
    ArrayList<Clothe> listTshirt, listPans, listShoes,rawClothes;
    ClotheAdapter adapterTshirt, adapterPans, adapterShoes;
    InfiniteScrollAdapter infiniteTshirt,infinitePans,infiniteShoes;
    TextView tvThirtEmpty, tvPansEmpty, tvShoesEmpty;
    PageScrollView psvTshirt,psvPans,psvShoes;
    private double mTemp = -1000d;
    private double minTemp = -1000d;
    private double maxTemp = -1000d;
    private boolean isEnabled = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            getArgs();
        }
    }

    private void getArgs(){
        rawClothes = getArguments().getParcelableArrayList(SELECTION_INFINITE_FRAGMENT_RAWR_CLOTHES);
        initData(rawClothes);
    }

    public void initData(List<Clothe> clothes) {
        if (clothes != null) {
            rawClothes = new ArrayList<>();
            rawClothes.addAll(clothes);
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
            filtreClothe(100, -100);
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
//        psvTshirt = view.findViewById(R.id.psv_thirt);
//        psvTshirt.setOrientation(Orientation.HORIZONTAL);
//        infiniteTshirt = InfiniteScrollAdapter.wrap(adapterTshirt);
//        psvTshirt.setAdapter(infiniteScrollAdapter);
//        psvTshirt.setItemTransitionTimeMillis(400);
//        psvTshirt.setItemTransformer(new ScaleTransformer.Builder()
//                .setMinScale(0.8f)
//                .build());
//        adapter.setData(listTshirt);
        tvThirtEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_tshirt_empty);
        tvPansEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_pans_empty);
        tvShoesEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_shoes_empty);

        psvTshirt = view.findViewById(R.id.psv_thirt);
        psvTshirt.setOrientation(Orientation.HORIZONTAL);
        adapterTshirt = new ClotheAdapter(listTshirt, this::itemSelected);
        if(listTshirt.size()>1) {
            infiniteTshirt = InfiniteScrollAdapter.wrap(adapterTshirt);
            psvTshirt.setAdapter(infiniteTshirt);
            psvTshirt.setItemTransitionTimeMillis(400);
            psvTshirt.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
            psvTshirt.setOffscreenItems(3);
        } else {
            psvTshirt.setAdapter(adapterTshirt);
        }

        psvPans = view.findViewById(R.id.psv_pans);
        psvPans.setOrientation(Orientation.HORIZONTAL);
        adapterPans = new ClotheAdapter(listPans, this::itemSelected);
        if(listPans.size()>1) {
            infinitePans = InfiniteScrollAdapter.wrap(adapterPans);
            psvPans.setAdapter(infinitePans);
            psvPans.setItemTransitionTimeMillis(400);
            psvPans.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
        } else {
            psvPans.setAdapter(adapterPans);
        }

        psvShoes = view.findViewById(R.id.psv_shoes);
        psvShoes.setOrientation(Orientation.HORIZONTAL);
        adapterShoes = new ClotheAdapter(listShoes, this::itemSelected);
        if(listShoes.size()>1) {
            infiniteShoes = InfiniteScrollAdapter.wrap(adapterShoes);
            psvShoes.setAdapter(infiniteShoes);
            psvPans.setItemTransitionTimeMillis(400);
            psvPans.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
        } else{
            psvShoes.setAdapter(adapterShoes);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadingListener.completeLoading();
        isEnabled = true;
    }


    private void filtreClothe(double minTemp, double maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        if (isEnabled) {
//            horizontalInfiniteCycleViewPagerTshirt.invalidate();
//            horizontalInfiniteCycleViewPagerTshirt.removeAllViews();
//            horizontalInfiniteCycleViewPagerTshirt.setAdapter(null);
//            horizontalInfiniteCycleViewPagerPans.invalidate();
//            horizontalInfiniteCycleViewPagerPans.removeAllViews();
//            horizontalInfiniteCycleViewPagerPans.setAdapter(null);
//            horizontalInfiniteCycleViewPagerShoes.invalidate();
//            horizontalInfiniteCycleViewPagerShoes.removeAllViews();
//            horizontalInfiniteCycleViewPagerShoes.setAdapter(null);
            listTshirt.clear();
            listPans.clear();
            listShoes.clear();
            for (int i = 0; i < rawClothes.size(); i++) {
                switch (rawClothes.get(i).getType()) {
                    case Clothe.TYPE_THIRT:
                        listTshirt.add(rawClothes.get(i));
                        break;
                    case Clothe.TYPE_PANS:
                        listPans.add(rawClothes.get(i));
                        break;
                    case Clothe.TYPE_SHOES:
                        listShoes.add(rawClothes.get(i));
                        break;
                }
            }
            ArrayList<Clothe> filtredClothes = new ArrayList<>();
            for (int i = 0; i < listTshirt.size(); i++) {
                if (listTshirt.get(i).getMinTemp() < minTemp && listTshirt.get(i).getMaxTemp() > maxTemp) {
                    filtredClothes.add(listTshirt.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
//                listTshirt = filtredClothes;
                adapterTshirt.setData(filtredClothes);
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
//                listPans = filtredClothes;
                adapterPans.setData(filtredClothes);
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
//                listShoes = filtredClothes;
                adapterShoes.setData(filtredClothes);
            } else {
                Log.d(TAG, "filtreClothe: no shoes found");
                showEmptyError(SHOES, "No shoes");
            }

        }
    }

    private void updateClothe(Clothe clothe) {
        switch (clothe.getType()) {
            case Clothe.TYPE_THIRT:
                Collections.sort(listTshirt);
                adapterTshirt.setItem(clothe);
                break;
            case Clothe.TYPE_PANS:
                Collections.sort(listPans);
                adapterPans.setItem(clothe);
                break;
            case Clothe.TYPE_SHOES:
                Collections.sort(listShoes);
                adapterShoes.setItem(clothe);
                break;
        }
        filtreClothe(minTemp,maxTemp);

    }

    private void showEmptyError(int type, String errorMessage) {
        if (tvPansEmpty != null) {
            switch (type) {
                case TSHIRT:
                    psvTshirt.setVisibility(View.GONE);
                    tvThirtEmpty.setVisibility(View.VISIBLE);
                    tvThirtEmpty.setText(errorMessage);
                    break;
                case PANS:
                    psvPans.setVisibility(View.GONE);
                    tvPansEmpty.setVisibility(View.VISIBLE);
                    tvPansEmpty.setText(errorMessage);
                    break;
                case SHOES:
                    psvShoes.setVisibility(View.GONE);
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
            if(mTemp!=-100) {
                filtreClothe(mTemp, mTemp);
            }else {
                filtreClothe(100,-100);
            }
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
