package com.example.nds.choosetheclothe.selection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.base.BaseLoadingFragment;
import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.clothe.ClotheAdapter;

import java.util.ArrayList;

public class SelectionFragment extends BaseLoadingFragment {

    RecyclerView rvTshirt, rvPans, rvShoes;
    ClotheAdapter adapterThsirt, adapterPans, adapterShoes;
    ArrayList<Clothe> listTshirt, listPans, listShoes;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLists();
        initAdapters();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected, container, false);
        rvTshirt = view.findViewById(R.id.rv_selected_tshirt);
        rvPans = view.findViewById(R.id.rv_selected_pans);
        rvShoes = view.findViewById(R.id.rv_selected_shoes);
        return view;
    }

    private void initLists() {
//        listTshirt = new ArrayList<>();
//        listTshirt.add(new Clothe(-3, 15, 5, R.drawable.thirt_warm, "tWarm"));
//        listTshirt.add(new Clothe(13, 20, 5, R.drawable.thirt_midle, "tMiddle"));
//        listTshirt.add(new Clothe(17, 30, 5, R.drawable.thirt_cold, "tCold"));
//        listPans = new ArrayList<>();
//        listPans.add(new Clothe(-3, 15, 5, R.drawable.pans_warm, "pWarm"));
//        listPans.add(new Clothe(13, 20, 5, R.drawable.pans_midle, "pMiddle"));
//        listPans.add(new Clothe(17, 30, 5, R.drawable.pans_cold, "pCold"));
//        listShoes = new ArrayList<>();
//        listShoes.add(new Clothe(-3, 15, 5, R.drawable.shoes_warm, "sWarm"));
//        listShoes.add(new Clothe(13, 20, 5, R.drawable.shoes_midle, "sMiddle"));
//        listShoes.add(new Clothe(17, 30, 5, R.drawable.shoes_cold, "sCold"));
    }

    private void initAdapters() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapterThsirt = new ClotheAdapter(listTshirt);
        adapterPans = new ClotheAdapter(listPans);
        adapterShoes = new ClotheAdapter(listShoes);
        rvTshirt.setLayoutManager(linearLayoutManager1);
        rvTshirt.setAdapter(adapterThsirt);
        rvPans.setLayoutManager(linearLayoutManager2);
        rvPans.setAdapter(adapterPans);
        rvShoes.setLayoutManager(linearLayoutManager3);
        rvShoes.setAdapter(adapterShoes);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadingListener.completeLoading();
    }
}
