package fr.waltermarighetto.reunion.model;

import androidx.annotation.NonNull;

public class Room {
     @NonNull
    private String mName;

     @NonNull
    public String getName() {
        return mName;
    }

    public void setName( @NonNull
                        String name) {
        mName = name;

    }
}
