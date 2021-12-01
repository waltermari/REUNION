package fr.waltermarighetto.reunion.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.FilterMeetings;
import fr.waltermarighetto.reunion.controller.MainActivity;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;
import fr.waltermarighetto.reunion.views.NewMeetingDialog;

public class FilterMeetingsDialog extends DialogFragment {

    ImageView clearRoom;
    Spinner filterRoomSpinner;
    EditText filterDateEditText;
    CalendarView filterCalendarView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_filter_meetings, null);

        prepareCalendar(v);
        prepareRoomSpinner(v);
        initWithCurrentFilters();

        AlertDialog dialog =  new AlertDialog.Builder(getActivity())
                .setTitle(R.string.filter_meetings)
                .setView(v)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Cancel
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //OK
                        FilterMeetings.getFilterRoom().clear();
                        String s = (String) filterRoomSpinner.getItemAtPosition(filterRoomSpinner
                                .getSelectedItemPosition());
                        if (!s.equals(getString(R.string.all_rooms))) {
                            FilterMeetings.getFilterRoom().add(s);
                            clearRoom.setVisibility(View.VISIBLE);
                        }

//                        Toast.makeText(getActivity(), "OK " + s, Toast.LENGTH_LONG).show();
                        FilterMeetings.FilterMeetings();
                        MainActivity.mMeetingsAdapter.notifyDataSetChanged();

                    }
                })
                .setNeutralButton(R.string.reset_all, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        filterRoomSpinner.setSelection(0);
                        clearRoom.setVisibility(View.GONE);
                        FilterMeetings.getFilterRoom().clear();
                        FilterMeetings.setFilterDate(null);
                        filterDateEditText.setText("");

                        FilterMeetings.FilterMeetings();
                        MainActivity.mMeetingsAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWithCurrentFilters() {
        clearRoom.setVisibility(View.GONE);
        filterRoomSpinner.setSelection(0);

        if (FilterMeetings.getFilterRoom().size() != 0) {
            for (String s : FilterMeetings.getFilterRoom()) {
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
        if  (FilterMeetings.getFilterDate() != null)
            filterDateEditText.setText(FilterMeetings.getFilterDate().format(InitData.dtfDate));
    }

    private void prepareCalendar(View view) {
        filterDateEditText = view.findViewById(R.id.filter_date);
        filterCalendarView = view.findViewById(R.id.filter_calendar);
        filterCalendarView.getDate();

        filterCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView filterCalendarView,
                                            int year, int month, int day) {

                FilterMeetings.setFilterDate(LocalDate.of(year, month+1, day));
                filterDateEditText.setText(FilterMeetings.getFilterDate().format(InitData.dtfDate));

            }
        });


    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void prepareRoomSpinner(@NonNull View view) {

        filterRoomSpinner = view.findViewById(R.id.filter_room);
        clearRoom = view.findViewById(R.id.clear_room);

        ArrayAdapter<String> aA = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);

        aA.add(getString(R.string.all_rooms));  // String vide pour dire qu'on sélectionne toutes les salles

        for (Room room : InitData.getRoomsGlobal()) aA.add(room.getName());
        filterRoomSpinner.setSelection(0); // par défaut rien de sélectionné
        clearRoom.setVisibility(View.GONE);
        filterRoomSpinner.setAdapter(aA);

        // juste après avoir sélectionné une salle de réunion dans le spinner, on positionne l'icone delete room

        filterRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                       int position, long id) {
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
            FilterMeetings.getFilterRoom().clear();

            clearRoom.setVisibility(View.GONE);
        });


    }

}

