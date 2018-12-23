package com.example.nds.choosetheclothe.clothe;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ClotheDao {
    @Query("SELECT * FROM clothe")
    List<Clothe> getAll();

    @Query("SELECT * FROM clothe WHERE id = :id")
    Clothe getById(long id);

    @Insert
    void insert(List<Clothe> employee);

    @Update
    void update(Clothe employee);

    @Delete
    void delete(Clothe employee);
}
