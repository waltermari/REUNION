package fr.waltermarighetto.reunion.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;


@RequiresApi(api = Build.VERSION_CODES.O)
public class FilterMeetings {

    private LocalDateTime timeToUseForChecks = LocalDateTime.now();
    private List<Meeting> meetings = new ArrayList<Meeting>();


    public ArrayList<Meeting> FilterMeetings() {


        // on supprime de la liste tous les meetings qui finissent avant timeToUseForChecks
        // (now si pas de filtre par date, 00h00 de la date du filtre sinon)
        if (InitData.filterDate == null)
            timeToUseForChecks = LocalDateTime.now();
        else timeToUseForChecks = (LocalDateTime) InitData.filterDate.atStartOfDay();

        for (fr.waltermarighetto.reunion.model.Meeting meet : InitData.mMeetingsGlobal) {
            if (meet.getEnd().isAfter(timeToUseForChecks)) { //le meeting n'est pas termminé

                if (InitData.filterDate == null || InitData.filterDate.compareTo(meet.getStart().toLocalDate()) ==0) {   // le meeting a une bonne date
                    if (InitData.getFilterRoom().size() > 0) { //on filtre par salle
                        for (String ro : InitData.getFilterRoom()) {
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


