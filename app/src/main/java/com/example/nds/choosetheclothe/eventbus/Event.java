package com.example.nds.choosetheclothe.eventbus;

public abstract class Event {
    public static final int UPDATE_TEMP = 1;
    protected int mEventType;

    public int getmEventType() {
        return mEventType;
    }
}
