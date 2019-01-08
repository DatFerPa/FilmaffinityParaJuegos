package com.filmaffinityparajuegos.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    private String name;
    private String password;

    public Usuario(String name, String password){
        this.name = name;
        this.password = password;
    }

    public Usuario(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
