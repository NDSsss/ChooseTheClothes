package com.example.nds.choosetheclothe.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.clothe.ClotheDao;

@Database(entities = {Clothe.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ClotheDao employeeDao();
}
