package fr.waltermarighetto.reunion.model;

import androidx.annotation.NonNull;

public class Room {
    @NonNull
   private  String mRoomName;

    @NonNull
    public Room getRoomName() {
        return this;
    }

    public Room setRoomName(@NonNull String roomName) {
       mRoomName = roomName;
       return this;
    }
}

