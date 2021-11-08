package fr.waltermarighetto.reunion.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.views.FilterMeetingsDialog;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;
import fr.waltermarighetto.reunion.views.NewMeetingDialog;

@SuppressLint({"NewApi", "ResourceType"})
@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    // pour la fenêtre de dialogue du filtre sur les réunions
    public static MeetingsAdapter mMeetingsAdapter;
    public static RecyclerView mRecycler;
    //    private Dialog filterMeetingsDialog;
    private SwipeRefreshLayout swipeRefreshLayout;


    //   String filterCalendar = new String();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new InitData();

        // préparation Recycler View
        mRecycler = findViewById(R.id.meetings_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mMeetingsAdapter = new MeetingsAdapter(new FilterMeetings().FilterMeetings());
        mRecycler.setAdapter(mMeetingsAdapter);


        // Refresh avec SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

             FilterMeetings.FilterMeetings();
             mMeetingsAdapter.notifyDataSetChanged();
             swipeRefreshLayout.setRefreshing(false);
            }
        });


        FloatingActionButton floatingNewMeeting = findViewById(R.id.new_meeting);
        floatingNewMeeting.setOnClickListener(view -> {
                    try {
                        new NewMeetingDialog(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                    NewMeetingDialog.newMeetingDialog.show();

                    // trop tôt il faut l'activer sur OK                   initRecyclerForMeetings();


                }
        );



    }



    /**    @RequiresApi(api = Build.VERSION_CODES.O)
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

    InitData.setFilterDate(LocalDate.of(year, month+1, day));
    filterDateEditText.setText(InitData.getFilterDate().format(InitData.dtfDate));

    }
    } );


    Spinner filterRoomSpinner;
    filterRoomSpinner = filterMeetingsDialog.findViewById(R.id.filter_room);
    clearRoom = filterMeetingsDialog.findViewById(R.id.clear_room);

    ArrayAdapter<String> aA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

    aA.add(getString(R.string.all_rooms));  // String vide pour dire qu'on ne sélectionne aucune room

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
    Toast.makeText(MainActivity.this, "CANCEL", Toast.LENGTH_LONG).show();
    filterMeetingsDialog.dismiss();
    });

    //OK
    filterOKButton = filterMeetingsDialog.findViewById(R.id.filter_ok);
    filterOKButton.setOnClickListener(fv -> {
    InitData.getFilterRoom().clear();
    String s = (String) filterRoomSpinner.getItemAtPosition(filterRoomSpinner.getSelectedItemPosition());
    if (!s.equals(getString(R.string.all_rooms))) {
    InitData.getFilterRoom().add(s);
    clearRoom.setVisibility(View.VISIBLE);
    }

    //           if (filterCalendarView.callOnClick()) {};

    // filterDate = filterCalendarView.getDate();
    //         filterDate = (DateFormat.DATE_FIELD) filterDateEditText.getText().toString();
    Toast.makeText(MainActivity.this, "OK " + s, Toast.LENGTH_LONG).show();
    filterMeetingsDialog.dismiss();
    initRecyclerForMeetings();

    });

    // CLEAR ROOM
    clearRoom.setOnClickListener(fv -> {

    filterRoomSpinner.setSelection(0);
    InitData.getFilterRoom().clear();
    clearRoom.setVisibility(View.GONE);
    });


    // CLEAR
    filterClearButton = filterMeetingsDialog.findViewById(R.id.filter_clear);
    filterClearButton.setOnClickListener(fv -> {


    filterRoomSpinner.setSelection(0);
    clearRoom.setVisibility(View.GONE);
    InitData.getFilterRoom().clear();
    InitData.setFilterDate(null);

    });

    }
     */
    // on met le bouton de filtre dans la barre d'actions

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        new FilterMeetingsDialog(this);
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
                FilterMeetingsDialog.filterMeetingsDialog.show();

                return (true);

        }
        return (super.onOptionsItemSelected(item));

    }
}





