package fr.waltermarighetto.reunion.views;

import static fr.waltermarighetto.reunion.model.InitData.dtfDateTime;
import static fr.waltermarighetto.reunion.model.InitData.dtfTime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.temporal.ChronoUnit;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.FilterMeetings;
import fr.waltermarighetto.reunion.controller.MainActivity;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.User;

public class DeleteMeetingDialog extends DialogFragment {
    int idToDelete;
    Context mContext;
    Meeting meeting_to_delete;
    String message;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  DeleteMeetingDialog(Context context, int id) {
        idToDelete = id;
        mContext = context;
        meeting_to_delete = FilterMeetings.getFilteredMeetings().get(idToDelete);

        // Build the message :
        message = context.getString(R.string.meeting_name) + "\n"
                + meeting_to_delete.getName().toString() + "\n\n"
                + context.getString(R.string.from) + " "
                + dtfDateTime.format(meeting_to_delete.getStart()) + " "
                + context.getString(R.string.to) + " "
                + dtfTime.format(meeting_to_delete.getEnd()) + "\n"
                + context.getString(R.string.meeting_duration) + " "
                + (ChronoUnit.SECONDS.between(meeting_to_delete.getStart(), meeting_to_delete.getEnd()) + 5) / 60
                + " minutes" + "\n\n"
                + context.getString(R.string.meeting_room) + "\n"
                + meeting_to_delete.getRoom().getName() + "\n\n"
                + context.getString(R.string.meeting_users) + "\n";


        String s = "";
        for (User u : meeting_to_delete.getUsers())
            s += u.getUser().toString() + "\n";
        message += s;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setTitle(mContext.getString(R.string.meeting_to_be_deleted))
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog, int id) {
                        //OK
                        for (int i = 0; i< InitData.getMeetingsGlobal().size(); i++ )
                            if (meeting_to_delete.equals(InitData.getMeetingsGlobal().get(i))) {
                                InitData.getMeetingsGlobal().remove(i);
                                break;
                            }
                        FilterMeetings.FilterMeetings();
                        MainActivity.mMeetingsAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Cancel
                    }
                }).create();

    }
}
