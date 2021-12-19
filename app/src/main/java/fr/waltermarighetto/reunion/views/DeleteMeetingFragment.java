package fr.waltermarighetto.reunion.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.FilterMeetings;
import fr.waltermarighetto.reunion.controller.MainActivity;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;

public class DeleteMeetingFragment extends DialogFragment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_delete_meeting, null);
 //       Meeting meetingToDelete = FilterMeetings.getFilteredMeetings().get(id)



        return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.meeting_to_be_deleted)
                .setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog, int id) {
                        //OK
                        for (int i = 0; i< InitData.getMeetingsGlobal().size(); i++ )
                            if (FilterMeetings.getFilteredMeetings().get(id).equals(InitData.getMeetingsGlobal().get(i))) {
                                InitData.getMeetingsGlobal().remove(i);
                                break;
                            }
                        FilterMeetings.FilterMeetings();
                        MainActivity.mMeetingsAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).create();
    }
}