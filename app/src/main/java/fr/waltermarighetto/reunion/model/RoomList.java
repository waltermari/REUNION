package fr.waltermarighetto.reunion.model;

import java.util.ArrayList;
import java.util.List;

public class RoomList {


    private ArrayList<Room> mRoomList = new ArrayList<Room>();

    public ArrayList<Room> getRoomList() {
        return mRoomList;
    }

    public void setRoomList(ArrayList<Room> roomList) {
        mRoomList = roomList;
    }

    public void addRoomInList(Room room, RoomList roomList) {
        if (!mRoomList.contains(room))
            mRoomList.add(room);
    }
    public void removeRoomFromList(Room room, RoomList roomList)  {
        if (mRoomList.contains(room))
            mRoomList.remove(room);
    }
}
   

