package fr.waltermarighetto.reunion.views;

import static fr.waltermarighetto.reunion.model.InitData.dtfDateTime;
import static fr.waltermarighetto.reunion.model.InitData.dtfTime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.temporal.ChronoUnit;
import java.util.List;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.FilterMeetings;
import fr.waltermarighetto.reunion.controller.MainActivity;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.User;

public class DeleteMeetingDialog extends Dialog {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DeleteMeetingDialog(@NonNull Context context, int id) {
        super(context);

        TextView deleteCancelButton, deleteOKButton;
        TextView meetingName, meetingTime, meetingUsers, meetingRoom;
        Meeting meeting_to_delete = InitData.mMeetingsGlobal.get(id);


 //       Dialog deleteMeetingDialog = new Dialog(context);
        setContentView(R.layout.dialog_delete_meeting);

        meetingName = findViewById(R.id.meeting_name);
        meetingName.setText(meeting_to_delete.getName());
        meetingTime = findViewById(R.id.meeting_time);
        String s1 = dtfDateTime.format(meeting_to_delete.getStart()) + " à " + dtfTime.format(meeting_to_delete.getEnd())
                +"\ndurée "
                +(ChronoUnit.SECONDS.between(meeting_to_delete.getStart(), meeting_to_delete.getEnd()) + 5) / 60
                + " minutes";
        meetingTime.setText(s1);
        meetingUsers = findViewById(R.id.meeting_users);
        String s = "";
        for (User u : meeting_to_delete.getUsers())
            s += u.getUser().toString() + ", ";
        meetingUsers.setText(s);
        meetingRoom = findViewById(R.id.meeting_room);
        meetingRoom.setText(meeting_to_delete.getRoom().getName());

        //Cancel

        deleteCancelButton = findViewById(R.id.delete_cancel);
        deleteCancelButton.setOnClickListener(fv -> {

            dismiss();
        });

        //OK
        deleteOKButton = findViewById(R.id.delete_ok);
        deleteOKButton.setOnClickListener(fv -> {
            InitData.mMeetingsGlobal.remove(id);
            FilterMeetings.FilterMeetings();
            MainActivity.mMeetingsAdapter.notifyDataSetChanged();
            dismiss();


        });


    }
}


