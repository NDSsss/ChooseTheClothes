package com.example.nds.choosetheclothe.selectioninfinite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    TextView tvThirtEmpty, tvPansEmpty, tvShoesEmpty;
    RecyclerView rvTShirt, rvPans, rvShoes;
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
        rvTShirt = view.findViewById(R.id.rv_thirt);
        rvPans = view.findViewById(R.id.rv_pans);
        rvShoes = view.findViewById(R.id.rv_shoes);
        adapterTshirt = new ClotheAdapter(getContext(),this::itemSelected);
        adapterPans = new ClotheAdapter(getContext(),this::itemSelected);
        adapterShoes = new ClotheAdapter(getContext(),this::itemSelected);
        LinearLayoutManager layoutManagerTshirt = new LinearLayoutManager(getContext());
        layoutManagerTshirt.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTShirt.setLayoutManager(layoutManagerTshirt);
        rvTShirt.setAdapter(adapterTshirt);
        LinearLayoutManager layoutManagerPans = new LinearLayoutManager(getContext());
        layoutManagerPans.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPans.setLayoutManager(layoutManagerPans);
        rvPans.setAdapter(adapterPans);
        LinearLayoutManager layoutManagerShoes = new LinearLayoutManager(getContext());
        layoutManagerShoes.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvShoes.setLayoutManager(layoutManagerShoes);
        rvShoes.setAdapter(adapterShoes);
        tvThirtEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_tshirt_empty);
        tvPansEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_pans_empty);
        tvShoesEmpty = view.findViewById(R.id.tv_fragment_infinite_scrolling_shoes_empty);
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
                Collections.sort(filtredClothes);
                adapterTshirt.setData(filtredClothes);
            } else {
                Log.d(TAG, "filtreClothe: no tshirts found");
                showEmptyError(TSHIRT, "Нет подходящей одежды\nДобавьте новую или измените меющююся одежду");
            }
            filtredClothes = new ArrayList<>();
            for (int i = 0; i < listPans.size(); i++) {
                if (listPans.get(i).getMinTemp() < minTemp && listPans.get(i).getMaxTemp() > maxTemp) {
                    filtredClothes.add(listPans.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                Collections.sort(filtredClothes);
                adapterPans.setData(filtredClothes);
            } else {
                Log.d(TAG, "filtreClothe: no pans found");
                showEmptyError(PANS, "Нет подходящей одежды\nДобавьте новую или измените меющююся одежду");
            }
            filtredClothes = new ArrayList<>();
            for (int i = 0; i < listShoes.size(); i++) {
                if (listShoes.get(i).getMinTemp() < minTemp && listShoes.get(i).getMaxTemp() > maxTemp) {
                    filtredClothes.add(listShoes.get(i));
                }
            }
            if (filtredClothes.size() > 0) {
                Collections.sort(filtredClothes);
                adapterShoes.setData(filtredClothes);
            } else {
                Log.d(TAG, "filtreClothe: no shoes found");
                showEmptyError(SHOES, "Нет подходящей одежды\nДобавьте новую или измените меющююся обувь");
            }

        }
    }

    private void updateClothe(Clothe clothe) {
        filtreClothe(minTemp,maxTemp);

    }

    private void showEmptyError(int type, String errorMessage) {
        if (tvPansEmpty != null) {
            switch (type) {
                case TSHIRT:
                    rvTShirt.setVisibility(View.GONE);
                    tvThirtEmpty.setVisibility(View.VISIBLE);
                    tvThirtEmpty.setText(errorMessage);
                    break;
                case PANS:
                    rvPans.setVisibility(View.GONE);
                    tvPansEmpty.setVisibility(View.VISIBLE);
                    tvPansEmpty.setText(errorMessage);
                    break;
                case SHOES:
                    rvShoes.setVisibility(View.GONE);
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
//                filtreClothe(-100,100);
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
