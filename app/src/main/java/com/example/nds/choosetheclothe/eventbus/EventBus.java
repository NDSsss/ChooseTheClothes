package com.example.nds.choosetheclothe.eventbus;

import android.widget.TextView;

import java.util.ArrayList;
public class EventBus {
    private ArrayList<IEventObserver> observers;
    public EventBus() {
        observers = new ArrayList<>();
    }

    public void addObserver(IEventObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IEventObserver observer){
        observers.remove(observer);
    }

    public void notifyEvent(Event event){
        for(int i = 0; i < observers.size();i++){
            observers.get(i).onEvent(event);
        }
    }

}
