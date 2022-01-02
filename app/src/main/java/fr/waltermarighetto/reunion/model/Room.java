package fr.waltermarighetto.reunion.model;

import androidx.annotation.NonNull;

public class Room {
    @NonNull
    private String mName;
    private int mColor;

    @NonNull
    public String getName() {  return mName; }
    public int getColor() {
        return mColor;
    }

    public void setName( @NonNull String name) { mName = name; }
    public void setColor( @NonNull int color) {
        mColor = color;

    }
}
