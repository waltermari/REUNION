package fr.waltermarighetto.reunion.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingList {
    private ArrayList<Meeting> mMeetingList = new ArrayList<Meeting>();

    public ArrayList<Meeting> getMeetingList() {
        return mMeetingList;
    }

    public void setMeetingList(ArrayList<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    public MeetingList addMeetingToList(Meeting meeting, MeetingList meetingList) {
        if (!mMeetingList.contains(meeting))
        mMeetingList.add(meeting);
        return meetingList;
    }
    public MeetingList removeMeetingFromList(Meeting meeting, MeetingList meetingList) {
        if (mMeetingList.contains(meeting))
        mMeetingList.remove(meeting);
        return meetingList;
    }
}
