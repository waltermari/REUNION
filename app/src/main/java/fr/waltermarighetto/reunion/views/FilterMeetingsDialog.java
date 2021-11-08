package fr.waltermarighetto.reunion.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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

public class FilterMeetingsDialog extends Dialog {
    TextView filterCancelButton, filterOKButton, filterClearButton;
    ImageView clearRoom, clearDate;
    public static Dialog filterMeetingsDialog;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  FilterMeetingsDialog(@NonNull Context context) {
        super(context);
     filterMeetingsDialog = new Dialog(context);
        filterMeetingsDialog.setContentView(R.layout.dialog_filter_meetings);

            CalendarView filterCalendarView;

            final EditText filterDateEditText;

            filterDateEditText = filterMeetingsDialog.findViewById(R.id.filter_date);
            filterCalendarView = filterMeetingsDialog.findViewById(R.id.filter_calendar);
            filterCalendarView.getDate();

            filterCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView filterCalendarView, int year, int month, int day) {

                    FilterMeetings.setFilterDate(LocalDate.of(year, month+1, day));
                    filterDateEditText.setText(FilterMeetings.getFilterDate().format(InitData.dtfDate));

                }
            } );


            Spinner filterRoomSpinner;
            filterRoomSpinner = filterMeetingsDialog.findViewById(R.id.filter_room);
            clearRoom = filterMeetingsDialog.findViewById(R.id.clear_room);

            ArrayAdapter<String> aA = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);

            aA.add(context.getString(R.string.all_rooms));  // String vide pour dire qu'on sélectionne toutes les salles

            for (Room room : InitData.mRoomsGlobal) aA.add(room.getName());
            filterRoomSpinner.setSelection(0); // par défaut rien de sélectionné
            clearRoom.setVisibility(View.GONE);
            filterRoomSpinner.setAdapter(aA);

            // juste après avoir sélectionné une salle de réunion dans le spinner, on positionne l'icone delete room

            filterRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if (filterRoomSpinner.getSelectedItemPosition() == 0)
                        clearRoom.setVisibility(View.GONE);
                    else clearRoom.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // code à écrire au cas où on agit sur rien sélectionné
                }

            });


            //Cancel
            filterCancelButton = filterMeetingsDialog.findViewById(R.id.filter_cancel);
            filterCancelButton.setOnClickListener(fv -> {
                Toast.makeText(context, "CANCEL", Toast.LENGTH_LONG).show();
                filterMeetingsDialog.dismiss();
            });

            //OK
            filterOKButton = filterMeetingsDialog.findViewById(R.id.filter_ok);
            filterOKButton.setOnClickListener(fv -> {
                FilterMeetings.getFilterRoom().clear();
                String s = (String) filterRoomSpinner.getItemAtPosition(filterRoomSpinner.getSelectedItemPosition());
                if (!s.equals(context.getString(R.string.all_rooms))) {
                    FilterMeetings.getFilterRoom().add(s);
                    clearRoom.setVisibility(View.VISIBLE);
                }

                Toast.makeText(context, "OK " + s, Toast.LENGTH_LONG).show();
                FilterMeetings.FilterMeetings();
                filterMeetingsDialog.dismiss();

               MainActivity.mMeetingsAdapter.notifyDataSetChanged();

            });

            // CLEAR ROOM
            clearRoom.setOnClickListener(fv -> {

                filterRoomSpinner.setSelection(0);
                FilterMeetings.getFilterRoom().clear();
                clearRoom.setVisibility(View.GONE);
            });


            // CLEAR
            filterClearButton = filterMeetingsDialog.findViewById(R.id.filter_clear);
            filterClearButton.setOnClickListener(fv -> {


                filterRoomSpinner.setSelection(0);
                clearRoom.setVisibility(View.GONE);
                FilterMeetings.getFilterRoom().clear();
                FilterMeetings.setFilterDate(null);

            });

        }
    }

