package com.example.nds.choosetheclothe.eventbus;

public abstract class Event {
    public static final int UPDATE_TEMP = 1;
    public static final int UPDATE_CLOTHE = 2;
    public static final int UPDATE_CLOTHE_IN_DB = 3;
    protected int mEventType;

    public int getmEventType() {
        return mEventType;
    }
}
