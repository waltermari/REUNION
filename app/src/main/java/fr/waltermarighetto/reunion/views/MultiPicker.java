package fr.waltermarighetto.reunion.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.location.GnssAntennaInfo;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.InitData;

public class MultiPicker extends DialogFragment  {
    private static Object mInstance;
    private String[] mValues;
    private boolean[] mSelectedItems;
    private String mTitle;
    private AlertDialog mDialog;
    private DialogInterface.OnClickListener mListener;

    public static MultiPicker getInstance() {
if (mInstance == null) {
    mInstance = new MultiPicker();
}
    return (MultiPicker) mInstance;
    }

    public MultiPicker MultiPicker(String[] values, boolean[] selected,  String title, DialogInterface.OnClickListener listener) {

        mSelectedItems = selected;
        mValues = values;
        mTitle = title;
        mListener= listener;
        return this;

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_multi_selection, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int toggleText = R.string.nothing;
            for (boolean b : mSelectedItems) if (!b) {toggleText = R.string.all; break;};
        mDialog = builder
                .setTitle(mTitle)
                .setMultiChoiceItems(mValues, mSelectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                                if (isChecked)
                                    mSelectedItems[which] = true;
                                else if (mSelectedItems[which])
                                    mSelectedItems[which] = false;
                            }
                        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Cancel on ne fait rien du tout
                        //                      clearSelectedValues();
                    }
                })
                .setPositiveButton(R.string.ok, mListener)
                .setNeutralButton( toggleText, null)
                .setCancelable(false)
                .create();
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {

                        Button button = ((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {


                                if (button.getText().toString().equals(getString(R.string.all))) {
                                    selectAllValues();


                                } else {
                                    clearSelectedValues();

                                }


                                //Dismiss once everything is OK.
//                                mDialog.dismiss();
                            }
                        });
                    }
                });
        return mDialog;
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