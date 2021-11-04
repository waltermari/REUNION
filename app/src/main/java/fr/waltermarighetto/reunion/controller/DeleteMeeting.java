package fr.waltermarighetto.reunion.controller;

import static fr.waltermarighetto.reunion.controller.InitResources.dtfDateTime;
import static fr.waltermarighetto.reunion.controller.InitResources.dtfTime;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.temporal.ChronoUnit;
import java.util.List;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;

public class DeleteMeeting extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createDialogDeleteMeeting(List<Meeting> meetings, int id) {
        TextView deleteCancelButton, deleteOKButton;
        TextView meetingName, meetingTime, meetingUsers, meetingRoom;
        Meeting meeting_to_delete = meetings.get(id);


        Dialog deleteMeetingDialog;
        deleteMeetingDialog= new Dialog(this);
        deleteMeetingDialog.setContentView(R.layout.dialog_delete_meeting);

        meetingName = deleteMeetingDialog.findViewById(R.id.meeting_name);
        meetingName.setText(meeting_to_delete.getName());
        meetingTime = deleteMeetingDialog.findViewById(R.id.meeting_time);
        meetingTime.setText(dtfDateTime.format(meeting_to_delete.getStart()) + " à " + dtfTime.format(meeting_to_delete.getEnd())
                + (ChronoUnit.SECONDS.between(meeting_to_delete.getStart(), meeting_to_delete.getEnd()) + 5) / 60
                + " minutes");
        meetingUsers = deleteMeetingDialog.findViewById(R.id.meeting_users);
        String s = "";
        for (User u : meeting_to_delete.getUsers())
            s += u + ", ";
        meetingUsers.setText(s);
        meetingRoom = deleteMeetingDialog.findViewById(R.id.meeting_room);
        meetingRoom.setText(meeting_to_delete.getRoom().getName());


//Cancel

        deleteCancelButton = deleteMeetingDialog.findViewById(R.id.delete_cancel);
        deleteCancelButton.setOnClickListener(fv -> {

            deleteMeetingDialog.dismiss();
        });

        //OK
        deleteOKButton = deleteMeetingDialog.findViewById(R.id.delete_ok);
        deleteOKButton.setOnClickListener(fv -> {
            meetings.remove(id);
            deleteMeetingDialog.dismiss();

            // MainActivity.initRecyclerForMeetings(deleteDate, deleteRoom);

        });
 //       MeetingsAdapter.MeetingsViewHolder(). . mImageRemove .setOnClickListener(createDialogDeleteMeeting(InitResources.mMeetingsGlobal,1));

    }
}
    /** 

    Spinner deleteRoomSpinner;
    deleteRoomSpinner =  deleteMeetingDialog.findViewById(R.id.delete_room);
    clearRoom = deleteMeetingDialog.findViewById(R.id.clear_room);

    ArrayAdapter<String> aA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

    aA.add("");  // String vide pour dire qu'on ne sélectionne aucune room

    for (Room room : InitResources.mRoomsGlobal)  aA.add(room.getName());
    deleteRoomSpinner.setSelection(0); // par défaut rien de sélectionné
    clearRoom.setVisibility(View.GONE);
    deleteRoomSpinner.setAdapter(aA);

    // juste après avoir sélectionné une salle de réunion dans le spinner, on positionne l'icone delete room

    deleteRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            if (deleteRoomSpinner.getSelectedItemPosition() == 0)
                clearRoom.setVisibility(View.GONE);
            else clearRoom.setVisibility(View.VISIBLE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // code à écrire au cas où on agit sur rien sélectionné
        }

    });





    //OK
    deleteOKButton= deleteMeetingDialog.findViewById(R.id.delete_ok);
    deleteOKButton.setOnClickListener(fv -> {
        deleteRoom.clear();
        String s = (String) deleteRoomSpinner.getItemAtPosition(deleteRoomSpinner.getSelectedItemPosition());
        if (!s.equals("")) {
            deleteRoom.add(s);
            clearRoom.setVisibility(View.VISIBLE); }
        //           if (deleteCalendarView.callOnClick()) {};

        // deleteDate = deleteCalendarView.getDate();
        //         deleteDate = (DateFormat.DATE_FIELD) deleteDateEditText.getText().toString();
        Toast.makeText(MainActivity.this, "OK " + s, Toast.LENGTH_LONG).show();
        deleteMeetingDialog.dismiss();
        initRecyclerForMeetings(deleteDate, deleteRoom);

    });

    // CLEAR ROOM
    clearRoom.setOnClickListener(fv -> {

        deleteRoomSpinner.setSelection(0);
        deleteRoom.clear();
        clearRoom.setVisibility(View.GONE);
    });



    // CLEAR
    deleteClearButton =  deleteMeetingDialog.findViewById(R.id.delete_clear);
    deleteClearButton.setOnClickListener(fv -> {

        deleteCalendar= null;
        //           deleteDateEditText.setText("");
        deleteRoomSpinner.setSelection(0);
        clearRoom.setVisibility(View.GONE);
        deleteRoom.clear();
        deleteDate = null;

    });



    deleteDateEditText =  deleteMeetingDialog.findViewById(R.id.delete_date);
    deleteCalendarView = deleteMeetingDialog.findViewById(R.id.delete_calendar);
}
*/