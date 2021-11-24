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
import fr.waltermarighetto.reunion.model.InitData;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UsersPicker extends DialogFragment {
    public boolean[] mSelectedItems = new boolean[InitData.getUsersGlobal().size()];
    String[] mUsersNames= new String[InitData.getUsersGlobal().size()];
    AlertDialog mDialog;

    public Dialog onCreateDialog(Bundle savedInstanceState){

 //       clearUsers();
        for (int i = 0; i < InitData.getUsersGlobal().size(); i++) {
            mUsersNames[i]=InitData.getUsersGlobal().get(i).getUser();
        }

        //on crée la vue à afficher
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_multi_selection, null);

        // on crée AlertDialog
        return mDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.select_users)
                .setMultiChoiceItems( mUsersNames, mSelectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                                if (isChecked)
                                    mSelectedItems[which] = true;
                                else
                                if (mSelectedItems[which])
                                    mSelectedItems[which] = false;
                            }
                        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Cancel
                        clearUsers();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //OK
 /**                       TextView meetingUsers = (TextView) ((AlertDialog) mDialog).findViewById(R.id.meeting_users);
                        boolean[] selection = getSelected();

                        String s="";
                        for (int i =0; i<selection.length; i++) {
                            if (selection[i])
                                s += InitData.getUsersGlobal().get(i).getUser().toString() + "\n";
                        }
                        meetingUsers.setText(s);
*/
                    }

                })
                .setNeutralButton(R.string.reset_all, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Select all
                        selectAllUsers();
                    }
                })
                .create();

    }

    public void clearUsers() {
        for (int i=0; i< InitData.getUsersGlobal().size(); i++)
            mSelectedItems[i]= false;
    }

    public void selectAllUsers() {
        for (int i=0; i< InitData.getUsersGlobal().size(); i++)
            mSelectedItems[i]= true;
    }
    public boolean[] getSelected() {
        return mSelectedItems;
    }
}
