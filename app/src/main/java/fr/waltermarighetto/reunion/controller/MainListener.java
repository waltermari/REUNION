package fr.waltermarighetto.reunion.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import fr.waltermarighetto.reunion.model.Meeting;

public interface MainListener {
    void onFiltersUpdated(ArrayList<String> roomNames, LocalDate date);
    void onNewMeetingUpdate(Meeting meeting);
}
