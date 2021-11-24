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

public class MultiPicker extends DialogFragment  {
    private String[] mValues;
    private boolean[] mSelectedItems;
    private String mTitle;
    private AlertDialog mDialog;


    public MultiPicker MultiPicker(String[] values, boolean[] selected,  String title) {

        mSelectedItems = selected;
        mValues = values;
        mTitle = title;
        return this;

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

         View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_multi_selection, null);

        return mDialog = new AlertDialog.Builder(getActivity())
                .setTitle(mTitle)
                .setMultiChoiceItems( mValues, mSelectedItems,
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
                        clearSelectedValues();
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
                .setNeutralButton(R.string.all, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Select all
                        selectAllValues();
                    }
                })
                .setCancelable(false)
                .create();
    }

    public void clearSelectedValues(){
        for (int i=0; i< mValues.length; i++)
            mSelectedItems[i]= false;
    }

    public void selectAllValues() {
        for (int i=0; i< mValues.length; i++)
            mSelectedItems[i]= true;
    }
    public boolean[] getSelected() {
        return mSelectedItems;
    }


}
