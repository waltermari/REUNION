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
    public void testMeeting() {
        Meeting mMeeting = new Meeting();
        mMeeting.setName("Meeting done for tests");
        Room room = new Room();
        room.setColor(10000);
        room.setName("Room for testing");
        mMeeting.setRoom(room);
        List<User> lUser = new ArrayList<>();
        User us0 = new User();
        us0.setUser("Machin0@lamzone.com");
        lUser.add(us0);
        User us1 = new User();
        us1.setUser("Machin1@lamzone.com");
        lUser.add(us1);
        User us2 = new User();
        us0.setUser("Machin2@lamzone.com");
        lUser.add(us2);
        User us3 = new User();
        us0.setUser("Machin3@lamzone.com");
        lUser.add(us3);
        User us4 = new User();
        us0.setUser("Machin4@lamzone.com");
        lUser.add(us4);
        mMeeting.setUsers(lUser);
        LocalDateTime mStart=LocalDateTime.now().minusMinutes(35);
        LocalDateTime mEnd=LocalDateTime.now().plusMinutes(18);
        mMeeting.setStart(mStart);
        mMeeting.setEnd(mEnd);

        assertEquals(mMeeting.getName(), "Meeting done for tests");
        assertEquals(mMeeting.getRoom(), room);
        assertEquals(mMeeting.getUsers(), lUser);
        assertEquals(mMeeting.getStart(), mStart);
        assertEquals(mMeeting.getEnd(), mEnd);

    }
}