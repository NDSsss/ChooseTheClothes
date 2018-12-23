package com.example.nds.choosetheclothe.clothe;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Clothe implements Parcelable,Comparable<Clothe> {
    public static final int TYPE_THIRT = 0;
    public static final int TYPE_PANS = 1;
    public static final int TYPE_SHOES = 2;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String imagePath;
    private int minTemp;
    private int maxTemp;
    private int raiting;
    private String name;
    private String comment;
    private int resId;
    private int type;

    public Clothe(int minTemp,int maxTemp,int raiting,int resId,String name,int type){
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.raiting = raiting;
        this.resId = resId;
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imagePath);
        dest.writeInt(this.minTemp);
        dest.writeInt(this.maxTemp);
        dest.writeInt(this.raiting);
        dest.writeString(this.name);
        dest.writeString(this.comment);
        dest.writeInt(this.resId);
        dest.writeInt(this.type);
    }

    public Clothe() {
    }

    protected Clothe(Parcel in) {
        this.id = in.readInt();
        this.imagePath = in.readString();
        this.minTemp = in.readInt();
        this.maxTemp = in.readInt();
        this.raiting = in.readInt();
        this.name = in.readString();
        this.comment = in.readString();
        this.resId = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<Clothe> CREATOR = new Creator<Clothe>() {
        @Override
        public Clothe createFromParcel(Parcel source) {
            return new Clothe(source);
        }

        @Override
        public Clothe[] newArray(int size) {
            return new Clothe[size];
        }
    };

    @Override
    public int compareTo(@NonNull Clothe o) {
        if(raiting>o.getRaiting()){
            return -1;
        } else if(raiting<o.getRaiting()){
            return 1;
        } else {
            return 0;
        }
    }
}
