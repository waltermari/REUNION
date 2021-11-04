package fr.waltermarighetto.reunion.controller;

import static java.time.LocalDate.*;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.location.GnssAntennaInfo;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;
@SuppressLint({"NewApi", "ResourceType"})     //??
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    // pour la fenêtre de dialogue du filtre sur les réunions

    private Dialog filterMeetingsDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    LocalDate filterDate;
    String filterCalendar = new String();
    List<String> filterRoom = new ArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new InitResources();

        filterDate = null;
        filterRoom.clear();
        initRecyclerForMeetings(filterDate, filterRoom);

        // Refresh avec SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                initRecyclerForMeetings(filterDate, filterRoom);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

 //       Dialog newMeetingDial = new Dialog(this);


        FloatingActionButton floatingNewMeeting = findViewById(R.id.new_meeting);
        floatingNewMeeting.setOnClickListener(view -> {
            try {
                new NewMeeting(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            NewMeeting.newMeetingDialog.show();

                    initRecyclerForMeetings(filterDate, filterRoom);


                }
        );



    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initRecyclerForMeetings(LocalDate date, List<String> room) {

        List<Meeting> meetings = new FilterMeetings().filterMeetings(date, room);
        RecyclerView mRecycler = findViewById(R.id.meetings_recycler_view);
        MeetingsAdapter mA = new MeetingsAdapter(meetings);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(mA);

        mA.notifyDataSetChanged();


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createDialogFilterMeetings() {
        TextView filterCancelButton, filterOKButton, filterClearButton;
        ImageView clearRoom, clearDate;


        filterMeetingsDialog = new Dialog(this);
        filterMeetingsDialog.setContentView(R.layout.dialog_filter_meetings);

        CalendarView filterCalendarview;
//        Calendar filterCalendar = Calendar.getInstance();
        final EditText filterDateEditText;

        filterDateEditText = filterMeetingsDialog.findViewById(R.id.filter_date);
        filterCalendarview = filterMeetingsDialog.findViewById(R.id.filter_calendar);
        filterCalendarview.getDate();

        filterCalendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView filterCalendarView, int year, int month, int day) {

                filterDate = LocalDate.of(year, month+1, day);
                filterDateEditText.setText(filterDate.format(InitResources.dtfDate));

            }
        } );


        Spinner filterRoomSpinner;
        filterRoomSpinner = filterMeetingsDialog.findViewById(R.id.filter_room);
        clearRoom = filterMeetingsDialog.findViewById(R.id.clear_room);

        ArrayAdapter<String> aA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        aA.add(getString(R.string.all_rooms));  // String vide pour dire qu'on ne sélectionne aucune room

        for (Room room : InitResources.mRoomsGlobal) aA.add(room.getName());
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
            Toast.makeText(MainActivity.this, "CANCEL", Toast.LENGTH_LONG).show();
            filterMeetingsDialog.dismiss();
        });

        //OK
        filterOKButton = filterMeetingsDialog.findViewById(R.id.filter_ok);
        filterOKButton.setOnClickListener(fv -> {
            filterRoom.clear();
            String s = (String) filterRoomSpinner.getItemAtPosition(filterRoomSpinner.getSelectedItemPosition());
            if (!s.equals(getString(R.string.all_rooms))) {
                filterRoom.add(s);
                clearRoom.setVisibility(View.VISIBLE);
            }
            //           if (filterCalendarView.callOnClick()) {};

            // filterDate = filterCalendarView.getDate();
            //         filterDate = (DateFormat.DATE_FIELD) filterDateEditText.getText().toString();
            Toast.makeText(MainActivity.this, "OK " + s, Toast.LENGTH_LONG).show();
            filterMeetingsDialog.dismiss();
            initRecyclerForMeetings(filterDate, filterRoom);

        });

        // CLEAR ROOM
        clearRoom.setOnClickListener(fv -> {

            filterRoomSpinner.setSelection(0);
            filterRoom.clear();
            clearRoom.setVisibility(View.GONE);
        });


        // CLEAR
        filterClearButton = filterMeetingsDialog.findViewById(R.id.filter_clear);
        filterClearButton.setOnClickListener(fv -> {

            //      filterCalendar = null;
            //           filterDateEditText.setText("");
            filterRoomSpinner.setSelection(0);
            clearRoom.setVisibility(View.GONE);
            filterRoom.clear();
            filterDate = null;

        });

    }

    // on met le bouton de filtre dans la barre d'actions

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        createDialogFilterMeetings();
        return true;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_meetings:

                //             Toast.makeText(this, R.string.app_name, Toast.LENGTH_LONG).show();
                //               Intent filterMeetingsActivityIntent = new Intent(MainActivity.this, FilterMeetings.class);
                //               startActivity(filterMeetingsActivityIntent);

                // Test avec dialogue

//                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//                PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.activity_filter_meetings, null, false),100,100, true);

//                pw.showAtLocation(this.findViewById(R.id.popup_content), Gravity.CENTER, 0, 0);

                filterMeetingsDialog.show();
                return (true);

        }
        return (super.onOptionsItemSelected(item));

    }
}





