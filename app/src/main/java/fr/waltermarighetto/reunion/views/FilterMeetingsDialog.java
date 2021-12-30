package fr.waltermarighetto.reunion.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.ArrayList;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.MainListener;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Room;

public class FilterMeetingsDialog extends DialogFragment {

    private ImageView clearRoom;
    private Spinner filterRoomSpinner;
    private EditText filterDateEditText;
    private static ArrayList<String> mRooms;
    private static LocalDate mDate;
    private MainListener mListener;
    private CalendarView filterCalendarView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle args = getArguments();
        String[]  roomsNames = args.getStringArray("ROOMS");
        mRooms= new ArrayList<String>();
        int i=0;
        while ( i<roomsNames.length) {
            mRooms.add(roomsNames[i]);
            i++;
        }
        if (args.getString("DATE") =="") mDate = null;
        else mDate = LocalDate.parse(args.getString("DATE"));

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_filter_meetings, null);

        prepareCalendar(v);
        prepareRoomSpinner(v);
        initWithCurrentFilters();

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.filter_meetings)
                .setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onFiltersUpdated(mRooms, mDate);
                    }
                })
                .setNeutralButton(R.string.reset_all, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Cancel
                    }
                })
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filterRoomSpinner.setSelection(0);
                        clearRoom.setVisibility(View.GONE);
                        mRooms.clear();
                        mDate = null;
                        filterDateEditText.setText("");
                    }
                });
            }
        });
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void prepareCalendar(View view) {
        filterDateEditText = view.findViewById(R.id.filter_date);

        filterCalendarView = view.findViewById(R.id.filter_calendar);
        filterCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView filterCalendarView,
                                            int year, int month, int day) {
                mDate = LocalDate.of(year, month+1, day);
                filterDateEditText.setText(mDate.format(InitData.dtfDate));

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void prepareRoomSpinner(@NonNull View view) {
        filterRoomSpinner = view.findViewById(R.id.filter_room);
        clearRoom = view.findViewById(R.id.clear_room);
        ArrayAdapter<String> aA = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        aA.add(getString(R.string.all_rooms));  // String pour dire qu'on sélectionne toutes les salles

        for (Room room : InitData.getRoomsGlobal()) aA.add(room.getName());
        filterRoomSpinner.setAdapter(aA);

        filterRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {

                String s = (String) filterRoomSpinner.getSelectedItem();
                if (!s.equals(getString(R.string.all_rooms))) {
                    mRooms.add(s);
                    clearRoom.setVisibility(View.VISIBLE);
                }

                if (filterRoomSpinner.getSelectedItemPosition() == 0)
                    clearRoom.setVisibility(View.GONE);
                else clearRoom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // code à écrire au cas où on agit sur rien sélectionné
            }

        });

        // CLEAR ROOM
        clearRoom.setOnClickListener(fv -> {
            filterRoomSpinner.setSelection(0);
            mRooms.clear();
            clearRoom.setVisibility(View.GONE);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWithCurrentFilters() {
        clearRoom.setVisibility(View.GONE);
        filterRoomSpinner.setSelection(0);

        if (mRooms.size() != 0) {
            for (String s : mRooms) {
                for( int i=0; i < InitData.getRoomsGlobal().size(); i++ ) {
                    if (s.toString().equals(InitData.getRoomsGlobal().get(i).getName())) {
                        filterRoomSpinner.setSelection(i+1);
                        clearRoom.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
        filterDateEditText.setText("");
        if  (mDate != null)
            filterDateEditText.setText(mDate.format(InitData.dtfDate));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (MainListener) context;
    }
}

