package com.example.nds.choosetheclothe.clothe;

import android.os.Parcel;
import android.os.Parcelable;

public class Clothe implements Parcelable {
    private String imagePath;
    private int minTemp;
    private int maxTemp;
    private int raiting;
    private String name;
    private String comment;
    private int resId;

    public Clothe(int minTemp,int maxTemp,int raiting,int resId,String name){
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.raiting = raiting;
        this.resId = resId;
        this.name = name;
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
        dest.writeString(this.imagePath);
        dest.writeInt(this.minTemp);
        dest.writeInt(this.maxTemp);
        dest.writeInt(this.raiting);
        dest.writeString(this.name);
        dest.writeString(this.comment);
        dest.writeInt(this.resId);
    }

    public Clothe() {
    }

    protected Clothe(Parcel in) {
        this.imagePath = in.readString();
        this.minTemp = in.readInt();
        this.maxTemp = in.readInt();
        this.raiting = in.readInt();
        this.name = in.readString();
        this.comment = in.readString();
        this.resId = in.readInt();
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
}
