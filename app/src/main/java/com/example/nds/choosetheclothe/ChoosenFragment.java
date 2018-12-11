package com.example.nds.choosetheclothe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nds.choosetheclothe.adapter.TshirtAdaper;

import java.util.ArrayList;

public class ChoosenFragment extends Fragment {
    private static final String TAG = "ChoosenFragment" ;
    RecyclerView rvTshirt,rvPants,rvShoes;
    ArrayList<Bitmap> alTshirts, alPants, alShoes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choosen,null);
        rvTshirt = (RecyclerView) view.findViewById(R.id.rv_fragment_choosen_tshirt);
        rvPants = (RecyclerView) view.findViewById(R.id.rv_fragment_choosen_pants);
        rvShoes = (RecyclerView) view.findViewById(R.id.rv_fragment_choosen_shoes);
        alTshirts = new ArrayList<>();
        alPants = new ArrayList<>();
        alShoes = new ArrayList<>();
        alTshirts.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alPants.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alShoes.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alTshirts.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alPants.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alShoes.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alTshirts.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alPants.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        alShoes.add(BitmapFactory.decodeResource(getResources(),R.drawable.ic_menu_camera));
        Log.d(TAG, "onCreateView: ");
        return view;
        
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        initAdapter();
    }
    
    private void initAdapter(){
        Log.d(TAG, "initAdapter: ");
        LinearLayoutManager thirtLinearLayoutManager = new LinearLayoutManager(getContext());
        thirtLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager pantsLinearLayoutManager = new LinearLayoutManager(getContext());
        pantsLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager shoesLinearLayoutManager = new LinearLayoutManager(getContext());
        shoesLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTshirt.setLayoutManager(thirtLinearLayoutManager);
        TshirtAdaper tshirtAdaper = new TshirtAdaper(alTshirts);
        rvTshirt.setAdapter(tshirtAdaper);
        rvPants.setLayoutManager(pantsLinearLayoutManager);
        TshirtAdaper pantsAdaper = new TshirtAdaper(alPants);
        rvPants.setAdapter(pantsAdaper);
        rvShoes.setLayoutManager(shoesLinearLayoutManager);
        TshirtAdaper shoesAdaper = new TshirtAdaper(alShoes);
        rvShoes.setAdapter(shoesAdaper);
    }
    
    public void refresh(){
        Log.d(TAG, "refresh: ");
        initAdapter();
    }
}
