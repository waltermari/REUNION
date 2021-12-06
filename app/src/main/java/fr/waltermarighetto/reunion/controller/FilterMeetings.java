package fr.waltermarighetto.reunion.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;


@RequiresApi(api = Build.VERSION_CODES.O)
public class FilterMeetings {

    private static LocalDate filterDate = null;
    private static List<String> filterRoom = new ArrayList<String>();

    private static LocalDateTime timeToUseForChecks = LocalDateTime.now();
    private final static List<Meeting> meetings = new ArrayList<Meeting>();


    public static List<Meeting> FilterMeetings() {

        meetings.clear();

        // on supprime de la liste tous les meetings qui finissent avant timeToUseForChecks
        // (now si pas de filtre par date, 00h00 de la date du filtre sinon)
        if (filterDate == null)
            timeToUseForChecks = LocalDateTime.now();
        else timeToUseForChecks = (LocalDateTime) filterDate.atStartOfDay();

        for (Meeting meet : InitData.getMeetingsGlobal()) {
            if (meet.getEnd().isAfter(timeToUseForChecks)) { //le meeting n'est pas termminé

                if (filterDate == null || filterDate.compareTo(meet.getStart().toLocalDate()) ==0) {   // le meeting a une bonne date
                    if (filterRoom.size() > 0) { //on filtre par salle
                        for (String ro : filterRoom) {
                            if (meet.getRoom().getName().equals(ro))
                                meetings.add((Meeting) meet);
                        }
                    } //on ne filtre pas par salle
                  else  meetings.add((Meeting) meet);
                } // on passe au meeting suivant

            }

        }

        return (ArrayList<Meeting>) meetings;
    }
    public static LocalDate getFilterDate() {
        return filterDate;
    }

    public static void setFilterDate(LocalDate date) {
        filterDate = date;
    }

    public static List<String> getFilterRoom() {
        return filterRoom;
    }

    public static void setFilterRoom(List<String> rooms) {
        if (rooms == null)
            filterRoom.clear();
        else filterRoom = rooms;
    }
    public static List<Meeting> getFilteredMeetings() {
        return meetings;
    }
}


