package com.example.nds.choosetheclothe.eventbus.events;

import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.eventbus.Event;

import java.util.List;

public class SettingClotheEvent extends Event {
    private List<Clothe> clothes;

    public SettingClotheEvent(List<Clothe> clothes){
        this.clothes = clothes;
        mEventType = Event.UPDATE_CLOTHE;
    }
    public List<Clothe> getClothes() {
        return clothes;
    }

    public void setClothes(List<Clothe> clothes) {
        this.clothes = clothes;
    }
}
