package fr.waltermarighetto.reunion.model;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting {
    private String mMeetingName;
    private Room mRoom;
    private UserList mUserList;
    private LocalDateTime mStart;
    private LocalDateTime mEnd;


    public void updateMeeting(String meetingName, Room room, UserList userList, LocalDateTime start, LocalDateTime end) {
        mMeetingName = meetingName;
        mRoom = room;
        mUserList = userList;
        mStart = start;
        mEnd = end;
    }
}
