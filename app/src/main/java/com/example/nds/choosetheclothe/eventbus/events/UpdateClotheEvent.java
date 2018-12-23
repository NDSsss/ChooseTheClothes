package com.example.nds.choosetheclothe.eventbus.events;

import android.arch.persistence.room.Update;

import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.eventbus.Event;

public class UpdateClotheEvent extends Event {
    private Clothe clothe;
    public UpdateClotheEvent(Clothe clothe){
        mEventType = Event.UPDATE_CLOTHE_IN_DB;
        this.clothe = clothe;
    }

    public Clothe getClothe() {
        return clothe;
    }
}
