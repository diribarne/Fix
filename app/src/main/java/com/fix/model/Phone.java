package com.fix.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Phone implements Parcelable {
    String type;
    String number;

    protected Phone(Parcel in) {
        type = in.readString();
        number = in.readString();
    }

    public String getName() {
        return number + "\n" + type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(number);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Phone> CREATOR = new Parcelable.Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public String getNumber() {
        return number;
    }
}