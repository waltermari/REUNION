package fr.waltermarighetto.reunion.model;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting {
    private String mName;
    private Room mRoom;
    private List<User> mUsers;
    private LocalDateTime mStart;
    private LocalDateTime mEnd;

    public String getName() {
        return mName;
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public Room getRoom() {
        return mRoom;
    }

    public LocalDateTime getStart() {
        return mStart;
    }

    public LocalDateTime getEnd() {
        return mEnd;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setUsers(List<User> ul) {
        mUsers = ul;
    }

    public void setRoom(Room r) {
        mRoom = r;
    }

    public void setStart(LocalDateTime t) {
        mStart = t;
    }

    public void setEnd(LocalDateTime t) {
        mEnd = t;
    }
}
