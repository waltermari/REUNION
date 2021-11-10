package fr.waltermarighetto.reunion.model;

import static java.time.LocalDateTime.now;

import android.graphics.Color;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InitData {



    public static List<User> mUsersGlobal;
    public static List<Room> mRoomsGlobal;
    public static List<Meeting> mMeetingsGlobal;

    public static DateTimeFormatter dtfDateTime = DateTimeFormatter.ofPattern("eeee dd-MM-YYYY HH'h'mm");
    public static DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("eeee dd-MM-YYYY");
    public static DateTimeFormatter dtfDateShort = DateTimeFormatter.ofPattern("dd-MM-YYYY");
    public static DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH'h'mm");
    public static SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd/MM/yy", Locale.FRANCE);

    {

        mUsersGlobal = new ArrayList<User>();
        mRoomsGlobal = new ArrayList<Room>();
        mMeetingsGlobal = new ArrayList<Meeting>();

        // on crée la liste globale des utilisateurs
        String[] utilisateurs = new String[]{"marc.martin@lamzone.com", "julie.rocca@lamzone.com",
                "herbert.leonard@lamzone.com", "marie.golotte@lamzone.com", "alain.possible@lamzone.com",
                "dominique.lasuite@lamzone.com", "utilisateur.lambda@lamzone.com", "claire.obscur@lamzone.com",
                "rene.clair@lamzone.com", "julien.paclair@lamzone.com", "marion.les@lamzone.com",
                "elisa.croche@lamzone.com", "marie.dubois@lamzone.com", "chaipa.ki@lamzone.com", "un.autre@lamzone.com",
                "au.suivant@lamzone.com", "cela.commence@lamzone.com", "abien.faire@lamzone.com",
                "raoul.tabille@lamzone.com", "hervé.palerater@lamzone.com", "jules.cesar@lamzone.com",
                "cesar.oscar@lamzone.com", "oscar.ibou@lamzone.com", "hibou.tantrin@lamzone.com",
                "train.train@lamzone.com", "quotidien.dujour@lamzone.com", "bientot.lafin@lamzone.com",
                "avant.dernier@lamzone.com", "dernierde.laliste@lamzone.com"};
        for (String util : utilisateurs) {
            User us = new User();
            us.setUser(util);
            mUsersGlobal.add(us);
        }


// on crée la liste globale des salles de réunions

        String[] salles = new String[]{"Bourgogne", "Corse", "Guadeloupe", "Aquitaine", "Bretagne",
                "Ile de France", "PACA", "Martinique", "Occitanie", "Auvergne"};


        for (String salle : salles) {
            Room ro = new Room();
            ro.setName(salle);

            mRoomsGlobal.add(ro);
        }

//	           On initialise la liste des réunions en se calant sur la date du jour pour mieux tester

        {

            Meeting mMeeting = new Meeting();
            mMeeting.setName("Budget");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(9);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(1));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(0));
            mUsers.add(mUsersGlobal.get(2));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(20));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(25));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Rigolade");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(0);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().plusMinutes(30));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(75));

            mMeetingsGlobal.add(mMeeting);
        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Congés annuels à plannifier");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(4);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(9));
            mUsers.add(mUsersGlobal.get(7));
            mUsers.add(mUsersGlobal.get(12));
            mUsers.add(mUsersGlobal.get(11));
            mUsers.add(mUsersGlobal.get(19));
            mUsers.add(mUsersGlobal.get(6));
            mUsers.add(mUsersGlobal.get(18));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(20));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(25));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Avec personne");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(5);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();
            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(5));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            mMeetingsGlobal.add(mMeeting);
        }

        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion A");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(3);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(16));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(20));
            mUsers.add(mUsersGlobal.get(19));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion B");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(8);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();


            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(7));
            mUsers.add(mUsersGlobal.get(19));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion C");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(9);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(5));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion D");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(7);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();


            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion E");

            Room mRoom = new Room();
            mRoom = mRoomsGlobal.get(8);
            mMeeting.setRoom(mRoom);

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(5));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            mMeetingsGlobal.add(mMeeting);

        }

        // on rajoute des meetings qui finissent dans quelques minutes
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("1 minute");
            mMeeting.setRoom(mRoomsGlobal.get(3));
            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(1));

            mMeetingsGlobal.add(mMeeting);

        }

        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("2 minutes");
            mMeeting.setRoom(mRoomsGlobal.get(3));

            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(2));

            mMeetingsGlobal.add(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("3 minutes");
            mMeeting.setRoom(mRoomsGlobal.get(3));
            List<User> mUsers = new ArrayList<User>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(3));

            mMeetingsGlobal.add(mMeeting);

        }
 //    mMeetingsGlobal.sort((Comparator<Meeting>  meeting, t1) -> {
 //        meeting.getStart().isAfter(t1.getStart())
 //                   LocaldateTime.compare
 //       });

    }

}