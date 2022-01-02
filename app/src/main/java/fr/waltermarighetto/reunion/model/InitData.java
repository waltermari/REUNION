package fr.waltermarighetto.reunion.model;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InitData {

    private static final List<User> mUsersGlobal;
    private static final List<Room> mRoomsGlobal;
    private static final List<Meeting> mMeetingsGlobal;
    // initialise les formats en fonction de la locale
    public static DateTimeFormatter dtfDateTime = DateTimeFormatter.ofPattern("eeee dd-MM-yyyy HH'h'mm");
    public static DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("eeee dd-MM-yyyy");
    public static DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH'h'mm");
    public static SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault());

    static {
        mUsersGlobal = new ArrayList<>();
        mRoomsGlobal = new ArrayList<>();
        mMeetingsGlobal = new ArrayList<>();

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

        // Tableau de couleur des salles
        int[] roomColors = {Color.BLUE, Color.GREEN, Color.BLACK, Color.RED,
                Color.CYAN, Color.GRAY, Color.MAGENTA, Color.YELLOW, Color.DKGRAY, Color.LTGRAY};

        for (int i=0; i< salles.length; i++) {
            Room ro = new Room();
            ro.setName(salles[i]);
            ro.setColor(roomColors[i]);
            mRoomsGlobal.add(ro);
        }

//	           On initialise la liste des réunions en se calant sur la date du jour pour mieux tester

        {

            Meeting mMeeting = new Meeting();
            mMeeting.setName("Budget");
            mMeeting.setRoom(mRoomsGlobal.get(9));

            List<User> mUsers = new ArrayList<>();
            mUsers.add(mUsersGlobal.get(1));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(0));
            mUsers.add(mUsersGlobal.get(2));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(20));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(25));

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Rigolade");
            mMeeting.setRoom(mRoomsGlobal.get(0));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().plusMinutes(30));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(75));

            addSortedMeeting(mMeeting);
        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Congés annuels à plannifier");
            mMeeting.setRoom(mRoomsGlobal.get(4));

            List<User> mUsers = new ArrayList<>();

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

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Avec personne");
            mMeeting.setRoom(mRoomsGlobal.get(5));

            List<User> mUsers = new ArrayList<>();
            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(5));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            addSortedMeeting(mMeeting);
        }

        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion A");
            mMeeting.setRoom(mRoomsGlobal.get(3));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(16));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(20));
            mUsers.add(mUsersGlobal.get(19));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion B");
            mMeeting.setRoom(mRoomsGlobal.get(8));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(7));
            mUsers.add(mUsersGlobal.get(19));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion C");
            mMeeting.setRoom(mRoomsGlobal.get(9));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(5));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion D");
            mMeeting.setRoom(mRoomsGlobal.get(7));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("Réunion E");
            mMeeting.setRoom(mRoomsGlobal.get(8));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(5));
            mUsers.add(mUsersGlobal.get(4));
            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(15));

            addSortedMeeting(mMeeting);

        }

        // on rajoute des meetings qui finissent dans quelques minutes
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("1 minute");
            mMeeting.setRoom(mRoomsGlobal.get(3));
            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));


            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(1));

            addSortedMeeting(mMeeting);

        }

        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("2 minutes");
            mMeeting.setRoom(mRoomsGlobal.get(3));

            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(2));

            addSortedMeeting(mMeeting);

        }
        {
            Meeting mMeeting = new Meeting();
            mMeeting.setName("3 minutes");
            mMeeting.setRoom(mRoomsGlobal.get(3));
            List<User> mUsers = new ArrayList<>();

            mUsers.add(mUsersGlobal.get(2));
            mUsers.add(mUsersGlobal.get(3));
            mUsers.add(mUsersGlobal.get(1));

            mMeeting.setUsers(mUsers);

            mMeeting.setStart(LocalDateTime.now().minusMinutes(10));
            mMeeting.setEnd(LocalDateTime.now().plusMinutes(3));

            addSortedMeeting(mMeeting);

        }

    }
    public static List<User> getUsersGlobal() {
        return mUsersGlobal;
    }

    public static List<Room> getRoomsGlobal(){
        return mRoomsGlobal;
    }

    public static List<Meeting> getMeetingsGlobal() {
        return mMeetingsGlobal;
    }

    public static void addSortedMeeting(Meeting meeting) {
        int size = mMeetingsGlobal.size();
        if ( size == 0) mMeetingsGlobal.add(meeting);

        else for ( int i=0; i<size; i++) {
            if (meeting.getStart().isBefore(mMeetingsGlobal.get(i).getStart())) {
                mMeetingsGlobal.add(i, meeting);
                break;
            }
        }
        if (size==mMeetingsGlobal.size())   mMeetingsGlobal.add(meeting);
    }
}