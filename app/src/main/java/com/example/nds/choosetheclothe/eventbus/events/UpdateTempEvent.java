package com.example.nds.choosetheclothe.eventbus.events;

import com.example.nds.choosetheclothe.eventbus.Event;

public class UpdateTempEvent extends Event {
    private double newTemp;
    public UpdateTempEvent(double temp){
        mEventType = Event.UPDATE_TEMP;
        newTemp = temp;
    }
}
