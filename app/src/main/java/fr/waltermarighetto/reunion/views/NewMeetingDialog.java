package fr.waltermarighetto.reunion.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.annotation.RequiresApi;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.FilterMeetings;
import fr.waltermarighetto.reunion.controller.MainActivity;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;

public class NewMeetingDialog extends Dialog {
    TextView newMeetingCancel, newMeetingReset, newMeetingOK, meetingEndTime, mandatoryRoom;
    ImageView clearMeetingName, clearNewRoom, resetDate, resetTime, resetDuration, clearUsers;
    EditText meetingName, meetingDate, meetingTime, meetingDuration;
    LocalDateTime mStart;
    LocalDateTime mEnd;
    TimePicker newMeetingTimePicker;
    Spinner roomSpinner;
    MultiSpinner usersSpinner;

    public static Dialog newMeetingDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NewMeetingDialog(Context context) throws IllegalAccessException, InstantiationException {
        super(context);
        newMeetingDialog = new Dialog(context);
        newMeetingDialog.setContentView(R.layout.dialog_new_meeting);

        createDialogNewMeeting();
        resetNewMeeting();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createDialogNewMeeting() {

// meeting Name
        meetingName = newMeetingDialog.findViewById(R.id.meeting_name);
        clearMeetingName = newMeetingDialog.findViewById(R.id.clear_meeting_name);

        meetingName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!meetingName.getText().toString().isEmpty()) {
                    clearMeetingName.setVisibility(View.VISIBLE);
                    meetingName.setBackgroundColor(Color.alpha(0));
                }   else  clearMeetingName.setVisibility(View.GONE);
            }
        });

        clearMeetingName.setOnClickListener(fv -> {
            meetingName.setText("");
            meetingName.setBackgroundColor(Color.alpha(0));
            clearMeetingName.setVisibility(View.GONE);
        });

// Gestion Jour, heure de  début, heure de fin et durée en minutes

        meetingDate = newMeetingDialog.findViewById(R.id.meeting_date);
        resetDate = newMeetingDialog.findViewById(R.id.reset_date);
        meetingTime = newMeetingDialog.findViewById(R.id.meeting_start_time);
        resetTime = newMeetingDialog.findViewById(R.id.reset_time);
        meetingDuration = newMeetingDialog.findViewById(R.id.meeting_duration);
        resetDuration = newMeetingDialog.findViewById(R.id.reset_duration);
        meetingEndTime = newMeetingDialog.findViewById(R.id.meeting_end_time);


// Date du meeting

        Calendar newMeetingCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newMeetingCalendar.set(Calendar.YEAR, year);
                newMeetingCalendar.set(Calendar.MONTH, monthOfYear);
                newMeetingCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                meetingDate.setText(InitData.sdf.format(newMeetingCalendar.getTime()));
                // on change la date sans changer l'heure
                mStart = LocalDateTime.of(year, monthOfYear+1, dayOfMonth, mStart.getHour(),
                        mStart.getMinute(), mStart.getSecond());
                // on calcule la date et heure de fin en fonction de la durée de la réunion
                endTimeCalculation();
                // on positionne le bouton de réinitialisation de la date
                resetDate.setVisibility(View.VISIBLE);
            }
        };

        meetingDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(meetingDate.getContext(),
                            date,
                            newMeetingCalendar.get(Calendar.YEAR),
                            newMeetingCalendar.get(Calendar.MONTH),
                            newMeetingCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    return (true);
                }
                return (false);
            }
        });

        resetDate.setOnClickListener(fv -> {
            meetingDate.setText(LocalDateTime.now().toLocalDate().format(InitData.dtfDate));
            //   mStart avec date réinitialisée et heure conservée

            mStart = LocalDateTime.of(LocalDateTime.now().getYear(),
                    LocalDateTime.now().getMonth(),
                    LocalDateTime.now().getDayOfMonth(),
                    mStart.getHour(),
                    mStart.getMinute(),
                    0);
            resetDate.setVisibility(View.GONE);
            endTimeCalculation();

        });
// Heure du meeting
        newMeetingTimePicker = newMeetingDialog.findViewById(R.id.time_picker);
        newMeetingTimePicker.setIs24HourView(true); // à mettre peut-être dans le profil utilisateur ou lié à la locale
        newMeetingTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                meetingTime.setText(hourOfDay + "h" + minute);
                mStart = LocalDateTime.of(mStart.getYear(), mStart.getMonth(), mStart.getDayOfMonth(),
                        hourOfDay, minute, 0);
                endTimeCalculation();
                resetTime.setVisibility(View.VISIBLE);
            }
        });

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                newMeetingTimePicker.setHour(hour);
                newMeetingTimePicker.setMinute(minute);
                meetingTime.setText(newMeetingTimePicker.getHour() + "h" + newMeetingTimePicker.getMinute());
                resetTime.setVisibility(View.VISIBLE);
            }
        };

        meetingTime.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new TimePickerDialog(meetingTime.getContext(),
                            timeSetListener,
                            newMeetingTimePicker.getHour(),
                            newMeetingTimePicker.getMinute(), false).show();
                    return (true);
                }
                return (false);
            }
        });

        resetTime.setOnClickListener(fv -> {
            meetingTime.setText(LocalDateTime.now().toLocalTime().format(InitData.dtfTime));
            mStart = LocalDateTime.of(mStart.getYear(),
                    mStart.getMonth(),
                    mStart.getDayOfMonth(),
                    LocalDateTime.now().getHour(),
                    LocalDateTime.now().getMinute(),
                    0);
            resetTime.setVisibility(View.GONE);
            endTimeCalculation();
        });

// Meeting Duration

        meetingDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {

                if (meetingDuration.getText().toString().equals(newMeetingDialog.getContext()
                        .getString(R.string.meeting_average_duration)))
                    resetDuration.setVisibility(View.GONE);
                else resetDuration.setVisibility(View.VISIBLE);
                endTimeCalculation();
            }
        });

        resetDuration.setOnClickListener(fv -> {
            meetingDuration.setText(newMeetingDialog.getContext().getString(R.string.meeting_average_duration));
            endTimeCalculation();
            resetDuration.setVisibility(View.GONE);
        });


// Gestion de la salle de réunion

        roomSpinner = newMeetingDialog.findViewById(R.id.meeting_room_spinner);
        clearNewRoom = newMeetingDialog.findViewById(R.id.clear_room);
        mandatoryRoom = newMeetingDialog.findViewById(R.id.mandatoryRoom);

        ArrayAdapter<String> roomPicklistAdaptor = new ArrayAdapter<String>(newMeetingDialog.getContext(),
                android.R.layout.simple_spinner_item);

        roomPicklistAdaptor.add(newMeetingDialog.getContext().getString(R.string.none_room));  // String vide pour dire qu'on ne sélectionne aucune room
        for (Room room : InitData.mRoomsGlobal) roomPicklistAdaptor.add(room.getName());

        roomSpinner.setSelection(0); // par défaut rien de sélectionné
        mandatoryRoom.setVisibility(View.VISIBLE);
        clearNewRoom.setVisibility(View.GONE);
        roomSpinner.setAdapter(roomPicklistAdaptor);

        // juste après avoir sélectionné une salle de réunion dans le spinner,
        // on positionne l'icone delete room et le champ mandatoryRoom
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (roomSpinner.getSelectedItemPosition() == 0) {
                    clearNewRoom.setVisibility(View.GONE);
                    mandatoryRoom.setVisibility(View.VISIBLE);
                } else {
                    clearNewRoom.setVisibility(View.VISIBLE);
                    mandatoryRoom.setVisibility(View.GONE);
                    roomSpinner.setBackgroundColor(Color.alpha(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // code à écrire au cas où on agit sur rien sélectionné
            }
        });

        clearNewRoom.setOnClickListener(fv -> {
            roomSpinner.setSelection(0); // par défaut rien de sélectionné
            clearNewRoom.setVisibility(View.GONE);
            mandatoryRoom.setVisibility(View.VISIBLE);
            roomSpinner.setBackgroundColor(Color.alpha(0));
        });

//////////////////////////////////////
        // gestion des utilisateurs avec MultisSpiner

        usersSpinner = newMeetingDialog.findViewById(R.id.meeting_users);
        clearUsers = newMeetingDialog.findViewById(R.id.clear_users);

        List<String> usersNames = new ArrayList<String>();
        usersNames.add(""); // String vide pour dire qu'on ne sélectionne personne

        for (User user : InitData.mUsersGlobal) usersNames.add(user.getUser());
        usersSpinner.setItems(usersNames, "Personne");

        // juste après avoir sélectionné une salle de réunion dans le spinner, on positionne l'icone delete room
        usersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (usersSpinner.getSelectedItemPosition() == 0)

                    clearUsers.setVisibility(View.GONE);
                else clearUsers.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // code à écrire au cas où on agit sur rien sélectionné
            }
        });
        clearUsers.setOnClickListener(fv -> {
            usersSpinner.setSelection(0); // par défaut rien de sélectionné
            clearUsers.setVisibility(View.GONE);
        });

        // Ok new meeting
        newMeetingOK = newMeetingDialog.findViewById(R.id.create_ok);
        newMeetingOK.setOnClickListener(fv -> {

            boolean complete = true;

            if (meetingName.getText().toString().isEmpty()) {
                complete = false;
                meetingName.setBackgroundColor(Color.RED);
            }
            if (roomSpinner.getSelectedItemPosition() == 0) {
                complete = false;
                roomSpinner.setBackgroundColor(Color.RED);
            }
            if (complete) {
                meetingName.setBackgroundColor(Color.alpha(0));
                roomSpinner.setBackgroundColor(Color.alpha(0));
                Meeting currentMeeting = new Meeting();
                currentMeeting.setName((meetingName.getText().toString()));
                for (Room r : InitData.mRoomsGlobal)
                    if (r.getName().toString().equals(roomPicklistAdaptor
                            .getItem(roomSpinner.getSelectedItemPosition()).toString())) {
                        currentMeeting.setRoom(r);
                        break;
                    }
                currentMeeting.setStart(mStart);
                currentMeeting.setEnd(mEnd);
// a revoir en fonction des personnes sélectionnées
                currentMeeting.setUsers(InitData.mUsersGlobal);
                InitData.mMeetingsGlobal.add(currentMeeting);
                FilterMeetings.FilterMeetings();
                MainActivity.mMeetingsAdapter.notifyDataSetChanged();
                newMeetingDialog.dismiss();
                resetNewMeeting();
            }
        });

        // Cancel new meeting
        newMeetingCancel = newMeetingDialog.findViewById(R.id.create_cancel);
        newMeetingCancel.setOnClickListener(fv -> {
            newMeetingDialog.dismiss();
            resetNewMeeting();
        });

        // Reset new meeting

        newMeetingReset = newMeetingDialog.findViewById(R.id.create_reset);
        newMeetingReset.setOnClickListener(fv -> {
            resetNewMeeting();
         });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void endTimeCalculation() {
        if ((meetingDuration.getText().toString().isEmpty())) mEnd=mStart;
        else   mEnd = mStart.plusMinutes(Integer.parseInt(meetingDuration.getText().toString()));
        int jours = mEnd.toLocalDate().compareTo(mStart.toLocalDate());
        String s="";
        if ( jours >0)   s = " (+" + jours + ")";

        meetingEndTime.setText(mEnd.toLocalTime().format(InitData.dtfTime)+s);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void resetNewMeeting() {
        meetingName.setBackgroundColor(Color.alpha(0));
        meetingName.setText("");
        meetingName.setHint(Html.fromHtml("<font color='red'>*</font><text>Objet de la réunion</text>"));
        clearMeetingName.setVisibility(View.GONE);

        meetingDate.setText(LocalDateTime.now().toLocalDate().format(InitData.dtfDate));
        mStart = LocalDateTime.now();
        resetDate.setVisibility(View.GONE);

        meetingTime.setText(LocalDateTime.now().toLocalTime().format(InitData.dtfTime));
        resetTime.setVisibility(View.GONE);

        meetingDuration.setText(newMeetingDialog.getContext().getString(R.string.meeting_average_duration));
        resetDuration.setVisibility(View.GONE);

        endTimeCalculation();

        roomSpinner.setSelection(0); // par défaut rien de sélectionné
        mandatoryRoom.setVisibility(View.VISIBLE);
        clearNewRoom.setVisibility(View.GONE);
        roomSpinner.setBackgroundColor(Color.alpha(0));

        usersSpinner.setSelection(0); // par défaut rien de sélectionné
        clearUsers.setVisibility(View.GONE);
    }

}

