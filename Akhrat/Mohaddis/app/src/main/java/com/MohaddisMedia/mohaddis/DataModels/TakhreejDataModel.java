package com.MohaddisMedia.mohaddis.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class TakhreejDataModel implements Parcelable {
    int id;
    int hadeesIDPresent;
    int headeesTakhreejId;
    String bookNamePresent;
    String bookNameTakhreej;
    int bookIdPresent;
    int bookIdTakhreej;
    int availability;

    public TakhreejDataModel() {
    }

    public TakhreejDataModel(int id, int hadeesIDPresent, int headeesTakhreejId, String bookNamePresent, String bookNameTakhreej, int bookIdPresent, int bookIdTakhreej, int availability) {
        this.id = id;
        this.hadeesIDPresent = hadeesIDPresent;
        this.headeesTakhreejId = headeesTakhreejId;
        this.bookNamePresent = bookNamePresent;
        this.bookNameTakhreej = bookNameTakhreej;
        this.bookIdPresent = bookIdPresent;
        this.bookIdTakhreej = bookIdTakhreej;
        this.availability = availability;
    }


    protected TakhreejDataModel(Parcel in) {
        id = in.readInt();
        hadeesIDPresent = in.readInt();
        headeesTakhreejId = in.readInt();
        bookNamePresent = in.readString();
        bookNameTakhreej = in.readString();
        bookIdPresent = in.readInt();
        bookIdTakhreej = in.readInt();
        availability = in.readInt();
    }

    public static final Creator<TakhreejDataModel> CREATOR = new Creator<TakhreejDataModel>() {
        @Override
        public TakhreejDataModel createFromParcel(Parcel in) {
            return new TakhreejDataModel(in);
        }

        @Override
        public TakhreejDataModel[] newArray(int size) {
            return new TakhreejDataModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHadeesIDPresent() {
        return hadeesIDPresent;
    }

    public void setHadeesIDPresent(int hadeesIDPresent) {
        this.hadeesIDPresent = hadeesIDPresent;
    }

    public int getHeadeesTakhreejId() {
        return headeesTakhreejId;
    }

    public void setHeadeesTakhreejId(int headeesTakhreejId) {
        this.headeesTakhreejId = headeesTakhreejId;
    }

    public String getBookNamePresent() {
        return bookNamePresent;
    }

    public void setBookNamePresent(String bookNamePresent) {
        this.bookNamePresent = bookNamePresent;
    }

    public String getBookNameTakhreej() {
        return bookNameTakhreej;
    }

    public void setBookNameTakhreej(String bookNameTakhreej) {
        this.bookNameTakhreej = bookNameTakhreej;
    }

    public int getBookIdPresent() {
        return bookIdPresent;
    }

    public void setBookIdPresent(int bookIdPresent) {
        this.bookIdPresent = bookIdPresent;
    }

    public int getBookIdTakhreej() {
        return bookIdTakhreej;
    }

    public void setBookIdTakhreej(int bookIdTakhreej) {
        this.bookIdTakhreej = bookIdTakhreej;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(hadeesIDPresent);
        dest.writeInt(headeesTakhreejId);
        dest.writeString(bookNamePresent);
        dest.writeString(bookNameTakhreej);
        dest.writeInt(bookIdPresent);
        dest.writeInt(bookIdTakhreej);
        dest.writeInt(availability);
    }
}
