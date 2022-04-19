package com.example.cs160cashew;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Budget implements Parcelable {

    private String name;
    private List<Category> categoryList = new ArrayList<Category>();
    private int limit;
    private double progress;
    Budget(String n, Category c, int l, double p){
        name = n;
        categoryList.add(c);
        limit = l;
        progress = p;
    }

    Budget(String n, int l, double p){
        name = n;
        limit = l;
        progress = p;
    }

    protected Budget(Parcel in) {

        name = in.readString();
        in.readTypedList(categoryList, Category.CREATOR);
        limit = in.readInt();
        progress = in.readDouble();

    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeTypedList(categoryList);
        dest.writeInt(limit);

        dest.writeDouble(progress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Budget> CREATOR = new Creator<Budget>() {
        @Override
        public Budget createFromParcel(Parcel in) {
            return new Budget(in);
        }

        @Override
        public Budget[] newArray(int size) {
            return new Budget[size];
        }
    };

    public void addCategory(Category c){
        categoryList.add(c);
    }

    public void updateProgress(double newProgress){progress -= newProgress;}

    public String getName(){
        return name;
    }

    public int getLimit(){
        return limit;
    }

    public double getProgress(){
        return progress;
    }

    public List<Category> getCategoryList(){
        return categoryList;
    }
}
