package com.example.nds.choosetheclothe.adding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.nds.choosetheclothe.R;
import com.example.nds.choosetheclothe.base.BaseLoadingFragment;
import com.example.nds.choosetheclothe.interfaces.ILoadingListener;

public class AddingFragment extends BaseLoadingFragment {

    GridView gvAlbub;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding,container,false);
        gvAlbub = view.findViewById(R.id.gv_fragment_adding);
        ((Button)view.findViewById(R.id.btn_adding)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)v).setText("Clicked");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadingListener.completeLoading();
    }
}
