package com.example.nds.choosetheclothe.interfaces;

public interface ILoadingListener{
    void startLoading();
    void completeLoading();
    void errorLoading(String errorMessage);
}
