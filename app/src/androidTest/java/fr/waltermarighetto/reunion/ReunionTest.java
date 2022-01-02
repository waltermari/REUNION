package fr.waltermarighetto.reunion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;

public class ReunionTest {
    List<Meeting> meetingList = new ArrayList<>();
    @Test
    public void testUsers() {
        User mUser;
        String mString;
        String domaine;
        mUser = new User();

        mString = "TEST des utilisateurs avec n'importe quoi comme "
                + "texte depuis une chaine vide et plein " +
                "de caractères spéciaux comme $%#{}=ç<>!:;,";

        domaine = "@lamzone.com";


        for (int i = 0; i < mString.length(); i++) {
            for (int j = 0; j <= i; j++) {

                mUser.setUser(mString.substring(j, i) + domaine);
                assertEquals(mUser.getUser(), mString.substring(j, i) + domaine);
            }
        }
    }
    @Test
    public void testRooms() {

        Room mRoom = new Room();
        String mString = "Sallederéuniontrèslongueavecbeaucoupdecaracteresdanslenompour"+
                "testersicelafonctionnebienavecledébordementéventuel";

        int j = 0;
        for (int i = 0; i < mString.length(); i++) {
            mRoom.setName(mString.substring(i));
            mRoom.setColor(j);
            assertEquals(mRoom.getName(), mString.substring(i));
            assertEquals(mRoom.getColor(),j);
            j++;
        }
    }
    @Test
    public void testMeetings() {
        Meeting mMeeting;
        Room[] lRoom = new Room[100];
        User[] lUser = new User[100];
        String meetingString, usersString, roomString, domaine;

        domaine = "@lamzone.com";
        meetingString = "PleindeMeetingsDeToutesLongueursAvecDesNomssTrèsVariables";
        roomString = "Sallederéuniontrèslongueavecbeaucoupdecaracteresdanslenompour"+
                "testersicelafonctionnebienavecledébordementéventuel";
        usersString = "TEST des utilisateurs avec n'importe quoi comme "
                + "texte depuis une chaine vide et plein " +
                "de caractères spéciaux comme $%#{}=ç<>!:;,";

        //       Création d'une centaine de Users

        for (int i =0; i<100; i++) {
            User  mUser = new User();
            mUser.setUser(usersString.substring((i)) + domaine);
            lUser[i] = mUser;
        }
        // création d'une centaine de salles

        int color = 1000000000;
        for (int i =0; i<100; i++) {

            Room mRoom = new Room();
            mRoom.setName(roomString.substring(i));
            mRoom.setColor(color);
            color++;
            lRoom[i] = mRoom;
        }


        // Création de Meetings



        for (int i = 0; i < meetingString.length(); i++) {
            mMeeting = new Meeting();
            for(int j=0;j<100; j++) {
                mMeeting.setRoom(lRoom[j]);

                for (int k=0; k<99; k++) {
                    List<User> lU = new ArrayList<>();


                    for (int l=0; l<(k); l++) {
                        lU.add(lUser[l]);

                    }
                    mMeeting.setUsers(lU);
                    assertEquals(mMeeting.getUsers(), lU);

                    mMeeting.setName(meetingString.substring(i));
                    LocalDateTime mStart=LocalDateTime.now().minusMinutes((long) (i+j)*5);
                    LocalDateTime mEnd=LocalDateTime.now().plusMinutes((long) (i+j)*10);
                    mMeeting.setStart(mStart);
                    mMeeting.setEnd(mEnd);

                    assertEquals(mMeeting.getName(), meetingString.substring(i));
                    assertEquals(mMeeting.getStart(), mStart);
                    assertEquals(mMeeting.getEnd(), mEnd);
                    assertEquals(mMeeting.getRoom(), lRoom[j]);
                }
            }
            meetingList.add(mMeeting);
            assertEquals(meetingList.get(i), mMeeting);
        }
    }
}