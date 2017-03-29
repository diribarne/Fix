package com.fix.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FixUser implements  Parcelable {
    String user_id;
    String first_name;
    String last_name;
    List<Phone> phones;
    String thumb;
    String photo;
    String birth_date;


    public List<Phone> getPhones() {
        return phones;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    public String getThumb() {
        return thumb;
    }

    public String getPhoto() {
        return photo;
    }

    protected FixUser(Parcel in) {
        user_id = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        if (in.readByte() == 0x01) {
            phones = new ArrayList<Phone>();
            in.readList(phones, Phone.class.getClassLoader());
        } else {
            phones = null;
        }
        thumb = in.readString();
        photo = in.readString();
        birth_date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        if (phones == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(phones);
        }
        dest.writeString(thumb);
        dest.writeString(photo);
        dest.writeString(birth_date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FixUser> CREATOR = new Parcelable.Creator<FixUser>() {
        @Override
        public FixUser createFromParcel(Parcel in) {
            return new FixUser(in);
        }

        @Override
        public FixUser[] newArray(int size) {
            return new FixUser[size];
        }
    };
}
