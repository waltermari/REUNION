package fr.waltermarighetto.reunion.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.MainActivity;
import fr.waltermarighetto.reunion.model.InitData;

public class MultiPicker extends DialogFragment  {
    //   private static Object mInstance;
    private static String[] mValues;
    private static boolean[] mSelectedItems;
    private static AlertDialog mDialog;
    private Context mContext;
    private DialogInterface.OnClickListener mListener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle args = getArguments();
        mValues = args.getStringArray("USERS");
        mSelectedItems =args.getBooleanArray("SELECTED");




//    public static MultiPicker getInstance(String[] values, boolean[] selected, String title,
//                                          DialogInterface.OnClickListener listener, Context context) {
//        if (mInstance == null) {
//            mInstance = new MultiPicker();
//        }
//        mSelectedItems = selected;
        //       mValues = values;
        String selectAllText = mContext.getString(R.string.all).toString();
        String selectNoneText = mContext.getString(R.string.nothing).toString();

        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_multi_selection, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        int toggleText = R.string.nothing;
        for (boolean b : mSelectedItems) if (!b) {toggleText = R.string.all; break;};
        mDialog = builder
                .setTitle(args.getString("TITLE"))
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
                        final ListView listview = mDialog.getListView();
                        if (button.getText().equals(selectAllText)) {
                            for (int i = 0; i < mValues.length; i++) {
                                mSelectedItems[i] = true;
                                listview.setItemChecked(i, true);
                                button.setText(selectNoneText);
                            }
                        } else {
                            for (int i = 0; i < mValues.length; i++) {
                                mSelectedItems[i] = false;
                                listview.setItemChecked(i, false);

                                button.setText(selectAllText);
                            }
                        }
                    }
                });
            }
        });
        //       mDialog.show();
        return mDialog;
        //      return (MultiPicker) mInstance;
    }

    public static void unselectAllValues(){
        for (int i=0; i< mValues.length; i++)
            mSelectedItems[i]= false;
    }

    public static void selectAllValues() {
        for (int i=0; i< mValues.length; i++)
            mSelectedItems[i]= true;
    }
    public static boolean[] getSelected() {
        return mSelectedItems;
    }
public void setListener(DialogInterface.OnClickListener listener) {
        mListener = listener;
}


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;


    }
}