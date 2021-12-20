package fr.waltermarighetto.reunion.views;

import static fr.waltermarighetto.reunion.model.InitData.dtfDateTime;
import static fr.waltermarighetto.reunion.model.InitData.dtfTime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import java.time.temporal.ChronoUnit;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.User;

public class DeleteMeetingDialog extends DialogFragment {

    Context mContext;
    Meeting meeting_to_delete;
    DialogInterface.OnClickListener mListener;


    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = mContext.getString(R.string.meeting_name) + "\n"
                + meeting_to_delete.getName().toString() + "\n\n"
                + mContext.getString(R.string.from) + " "
                + dtfDateTime.format(meeting_to_delete.getStart()) + " "
                + mContext.getString(R.string.to) + " "
                + dtfTime.format(meeting_to_delete.getEnd()) + "\n"
                + mContext.getString(R.string.meeting_duration) + " "
                + (ChronoUnit.SECONDS.between(meeting_to_delete.getStart(), meeting_to_delete.getEnd()) + 5) / 60
                + " minutes" + "\n\n"
                + mContext.getString(R.string.meeting_room) + "\n"
                + meeting_to_delete.getRoom().getName() + "\n\n"
                + mContext.getString(R.string.meeting_users) + "\n";

        String s = "";
        for (User u : meeting_to_delete.getUsers())
            s += u.getUser().toString() + "\n";
        message += s;



        // on crée AlertDialog
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setTitle(mContext.getString(R.string.meeting_to_be_deleted))
                .setPositiveButton(mContext.getString(R.string.ok), mListener)
                .setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Cancel
                    }
                }).create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMeetingToDelete(Meeting meetingToDelete) {
        this.meeting_to_delete = meetingToDelete;
    }
    void setListener(DialogInterface.OnClickListener listener) {
        mListener = listener;
    }
}
