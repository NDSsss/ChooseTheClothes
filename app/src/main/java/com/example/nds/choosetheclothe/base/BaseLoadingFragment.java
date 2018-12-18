package com.example.nds.choosetheclothe.base;

import android.support.v4.app.Fragment;

import com.example.nds.choosetheclothe.eventbus.Event;
import com.example.nds.choosetheclothe.eventbus.IEventObserver;
import com.example.nds.choosetheclothe.interfaces.ILoadingListener;

public class BaseLoadingFragment extends Fragment implements IEventObserver {

    protected ILoadingListener mLoadingListener;

    public void setLoadingListener(ILoadingListener listener){
        mLoadingListener = listener;
    }

    public void startLoading(){
        mLoadingListener.startLoading();
    }

    public void completeLoading(){
        mLoadingListener.completeLoading();
    }

    public void errorLoading(String message){
        mLoadingListener.errorLoading(message);
    }

    @Override
    public void onEvent(Event event) {

    }
}
