package fr.waltermarighetto.reunion.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;


@RequiresApi(api = Build.VERSION_CODES.O)
public class FilterMeetings {
    private List<Room> filterRoom; //= new ArrayList<Room>();
//    LocalDate filterDate;
    private LocalDateTime timeToUseForChecks = LocalDateTime.now();
    private List<Meeting> meetings = new ArrayList<Meeting>();
    //  List<Meeting> mMeetings = InitRessources.mMeetingsGlobal;
    //filterMeetings ( LocalDate null, List<Room> null);


    public ArrayList<Meeting> filterMeetings(LocalDate date, List<String> rooms_names) {
        //       List<Meeting> meetings = new List<Meeting>();

        //      filterDate = date;
        //       filterRoom = rooms;
        // on supprime de la liste tous les meetings qui finissent avant timeToUseForChecks
        // (now si pas de filtre par date, 00h00 de la date du filtre sinon)
        if (date == null)
            timeToUseForChecks = LocalDateTime.now();
        else timeToUseForChecks = (LocalDateTime) date.atStartOfDay();

        for (fr.waltermarighetto.reunion.model.Meeting meet : InitResources.mMeetingsGlobal) {
            if (meet.getEnd().isAfter(timeToUseForChecks)) { //le meeting n'est pas termminé

                if (date == null || date.compareTo(meet.getStart().toLocalDate()) ==0) {   // le meeting a une bonne date
                    if (rooms_names.size() > 0) { //on filtre par salle
                        for (String ro : rooms_names) {
                            if (meet.getRoom().getName().equals(ro))
                                meetings.add((Meeting) meet);
                        }
                    } //on ne filtre pas par salle
                  else  meetings.add((Meeting) meet);
                } // on passe au meeting suivant

            }

        }
        new MeetingsAdapter(meetings);
        return (ArrayList<Meeting>) meetings;
    }
}


