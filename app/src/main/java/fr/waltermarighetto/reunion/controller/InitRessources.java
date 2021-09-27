package fr.waltermarighetto.reunion.controller;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.MeetingList;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.RoomList;
import fr.waltermarighetto.reunion.model.User;
import fr.waltermarighetto.reunion.model.UserList;

public class InitRessources {

    public UserList mUserListGlobal;
private UserList mUserList;
    public RoomList mRoomListGlobal;
private Meeting mMeeting;
public MeetingList mMeetingListGlobal;
    {


        mUserListGlobal = new UserList();

        mRoomListGlobal = new RoomList();

        mMeetingListGlobal = new MeetingList();

        // on crée la liste globale des utilisateurs
        mUserListGlobal.addUserInList(new User().setUser("marc.martin@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("juile.rocca@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("herbert.leonard@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("marie.golotte@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("alain.possible@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("dominique.lasuite@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("utilisateur.lambda@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("claire.obscur@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("rene.clair@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("julien.paclair@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("marion.les@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("Elisa.croche@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("marie.dubois@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("chaipa.ki@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("un.autre@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("au.suivant@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("cela.commence@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("abien.faire@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("raoul.tabille@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("hervé.palerater@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("jules.cesar@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("cesar.oscar@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("oscar.ibou@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("hibou.tantrin@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("train.train@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("quotidien.dujour@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("bientot.lafin@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("avant.dernier@lamzone.com"), mUserListGlobal);
        mUserListGlobal.addUserInList(new User().setUser("dernierde.laliste@lamzone.com"), mUserListGlobal);
// on crée la liste globale des salles de réunions
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Auvergne"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Alsace"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Aquitaine"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Occitanie"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Ile De France"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Hauts De France"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Corse"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Guadeloupe"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("PACA"), mRoomListGlobal);
        mRoomListGlobal.addRoomInList(new Room().setRoomName("Martinique"), mRoomListGlobal);

//        On initialise la liste des réunions en se calant sur la date du jour pour mieux tester
        mMeeting = new Meeting();
        mUserList = new UserList();
        mUserList.addUserInList(mUserListGlobal.getUserList().get(1),mUserList);
        mUserList.addUserInList(mUserListGlobal.getUserList().get(5),mUserList);
        mUserList.addUserInList(mUserListGlobal.getUserList().get(7),mUserList);
        mUserList.addUserInList(mUserListGlobal.getUserList().get(12),mUserList);

        mMeeting.updateMeeting("Budget", mRoomListGlobal.getRoomList().get(5), mUserList, LocalDateTime.now(),LocalDateTime.now() );
        mMeetingListGlobal.addMeetingToList(mMeeting, mMeetingListGlobal);

    }

}
