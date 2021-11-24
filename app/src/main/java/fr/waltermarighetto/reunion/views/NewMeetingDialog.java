package fr.waltermarighetto.reunion.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Build;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


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

public class NewMeetingDialog extends DialogFragment {
    TextView  meetingEndTime, mandatoryRoom, meetingUsers;
    ImageView clearMeetingName, clearNewRoom, resetDate, resetTime, resetDuration, clearUsers;
    EditText meetingName, meetingDate, meetingTime, meetingDuration ;
    LocalDateTime mStart;
    LocalDateTime mEnd;
    TimePicker newMeetingTimePicker;
    Spinner roomSpinner;
    ArrayAdapter<String> roomPicklistAdaptor;
 //   UsersPicker usersPickerDialog;
    MultiPicker   usersPickerDialog;
// MultiSpinner usersSpinner;
boolean[] currentUsersSelection;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //on crée la vue à afficher
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_new_meeting,null);
        // on prépare les données et listeners pour meeting Name, Date, Time, Duration, Room, Users
        manageName(v);
        manageDate(v);
        manageTime(v);
        manageDuration(v);
        manageRoom(v);
        manageUsers(v);

        resetNewMeeting();

        // on crée AlertDialog

        AlertDialog.Builder alDi = new AlertDialog.Builder(getActivity());
        alDi
                .setTitle(R.string.new_meeting_title)
                .setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog, int id) {
                        // Ok new meeting
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
                            for (Room r : InitData.getRoomsGlobal())
                                if (r.getName().toString().equals(roomPicklistAdaptor
                                        .getItem(roomSpinner.getSelectedItemPosition()).toString())) {
                                    currentMeeting.setRoom(r);
                                    break;
                                }
                            currentMeeting.setStart(mStart);
                            currentMeeting.setEnd(mEnd);

                            // users

                            boolean[] selection = usersPickerDialog.getSelected();

                            String s="";
                            List<User> lUser= new ArrayList<User>();
                            for (int i =0; i<selection.length; i++) {
                                if(selection[i]) {
                                    s += InitData.getUsersGlobal().get(i).getUser().toString() + "\n";
                                    lUser.add(InitData.getUsersGlobal().get(i));
                                }
                            }
                            currentMeeting.setUsers(lUser);
                            meetingUsers.setText(s);


                            InitData.addSortedMeeting(currentMeeting);
                            FilterMeetings.FilterMeetings();
                            MainActivity.mMeetingsAdapter.notifyDataSetChanged();
                            resetNewMeeting();
                        } else {
                            //que faire ? on doit éviter le dismiss()
                            //alDi.create().show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Cancel
                    }
                })
                .setNeutralButton(R.string.reset_all, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog, int id) {
                        // Reset new meeting

                        resetNewMeeting();

                        // alDi.create().show();
                    }
                })
        .setCancelable(false);

        return alDi.create();
    }

    private void manageName(View view) {
        meetingName = view.findViewById(R.id.meeting_name);
        clearMeetingName = view.findViewById(R.id.clear_meeting_name);

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
                } else clearMeetingName.setVisibility(View.GONE);
            }
        });

        clearMeetingName.setOnClickListener(fv -> {
            meetingName.setText("");
            meetingName.setHint(Html.fromHtml("<font color='red'>* </font><text>Objet de la réunion</text>"));
            meetingName.setBackgroundColor(Color.alpha(0));
            clearMeetingName.setVisibility(View.GONE);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void manageDate(View view) {
        // Gestion Jour, heure de  début, heure de fin et durée en minutes
        meetingDate = view.findViewById(R.id.meeting_date);
        resetDate = view.findViewById(R.id.reset_date);

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
                mStart = LocalDateTime.of(year, monthOfYear + 1, dayOfMonth, mStart.getHour(),
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void manageTime(View view) {
        meetingTime = view.findViewById(R.id.meeting_start_time);
        resetTime = view.findViewById(R.id.reset_time);
        meetingEndTime = view.findViewById(R.id.meeting_end_time);

        /**       final TimePickerDialog newMeetingTimePickerDialog;

         meetingTime.setOnClickListener(new View.OnClickListener() {
        @Override
        public boolean onClick(View v) {

        newMeetingTimePickerDialog  = new TimePickerDialog( meetingTime.getContext(),
        new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker,  int hour, int minute) {
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        meetingTime.setText(timePicker.getHour() + "h" + timePicker.getMinute());
        resetTime.setVisibility(View.VISIBLE);
        }
        });
        return false;
        }

        });
         */
        View timePickerView = LayoutInflater.from(getActivity())
                .inflate(R.layout.time_picker,null);
        newMeetingTimePicker = timePickerView.findViewById(R.id.time_picker);
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

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void manageDuration(View view) {
        meetingDuration = view.findViewById(R.id.meeting_duration);
        resetDuration = view.findViewById(R.id.reset_duration);
        meetingDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (meetingDuration.getText().toString().equals(getActivity()
                        .getString(R.string.meeting_average_duration)))
                    resetDuration.setVisibility(View.GONE);
                else resetDuration.setVisibility(View.VISIBLE);
                endTimeCalculation();
            }
        });

        resetDuration.setOnClickListener(fv -> {
            meetingDuration.setText(getActivity().getString(R.string.meeting_average_duration));
            endTimeCalculation();
            resetDuration.setVisibility(View.GONE);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void manageRoom(View view) {
        roomPicklistAdaptor = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item);

        roomSpinner = view.findViewById(R.id.meeting_room_spinner);
        clearNewRoom = view.findViewById(R.id.clear_room);
        mandatoryRoom = view.findViewById(R.id.mandatoryRoom);

        roomPicklistAdaptor.add(getActivity().getString(R.string.none_room));  // String vide pour dire qu'on ne sélectionne aucune room
        for (Room room : InitData.getRoomsGlobal()) roomPicklistAdaptor.add(room.getName());

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

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void manageUsers(View view) {
        meetingUsers = view.findViewById(R.id.meeting_users);
        String[] usersNames = new String[InitData.getUsersGlobal().size()];
        currentUsersSelection = new boolean[InitData.getUsersGlobal().size()];

        for (int i = 0; i < InitData.getUsersGlobal().size(); i++) {
            usersNames[i] = InitData.getUsersGlobal().get(i).getUser();
            currentUsersSelection[i] = false;
        }

        meetingUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                usersPickerDialog = new MultiPicker().MultiPicker(usersNames, currentUsersSelection, getString(R.string.select_users));
          usersPickerDialog.show(manager, "users");


  //           manager.executePendingTransactions();

                usersPickerDialog.getDialog().setOnDismissListener( new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        boolean[] selection = usersPickerDialog.getSelected();

                        String s = "";
                        for (int i = 0; i < selection.length; i++) {
                            if (selection[i]) {
                                s += InitData.getUsersGlobal().get(i).getUser().toString() + "\n";
                                currentUsersSelection[i] = true;
                            }
                        }
                        meetingUsers.setText(s);
                        meetingUsers.setTextSize(10);
                    }
                });

            }
 /**           public void OnDismissListener() {
                boolean[] selection = usersPickerDialog.getSelected();

                String s="";
                for (int i =0; i<selection.length; i++) {
                    if (selection[i])
                    s += InitData.getUsersGlobal().get(i).getUser().toString() + "\n";
                }
                meetingUsers.setText(s);
            }
  */
        });


        clearUsers = view.findViewById(R.id.clear_users);

        clearUsers.setOnClickListener(fv -> {
            usersPickerDialog.clearSelectedValues();
            clearUsers.setVisibility(View.GONE);
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void resetNewMeeting() {
        meetingName.setBackgroundColor(Color.alpha(0));
        meetingName.setText("");
        meetingName.setHint(Html.fromHtml("<font color='red'>* </font><text>Objet de la réunion</text>"));
        clearMeetingName.setVisibility(View.GONE);

        meetingDate.setText(LocalDateTime.now().toLocalDate().format(InitData.dtfDate));
        mStart = LocalDateTime.now();
        resetDate.setVisibility(View.GONE);

        meetingTime.setText(LocalDateTime.now().toLocalTime().format(InitData.dtfTime));
        resetTime.setVisibility(View.GONE);

        meetingDuration.setText(getString(R.string.meeting_average_duration));
        resetDuration.setVisibility(View.GONE);

        endTimeCalculation();

        roomSpinner.setSelection(0); // par défaut rien de sélectionné
        mandatoryRoom.setVisibility(View.VISIBLE);
        clearNewRoom.setVisibility(View.GONE);
        roomSpinner.setBackgroundColor(Color.alpha(0));

        meetingUsers.setText(R.string.none_user);
//        usersPickerDialog.clearSelectedValues();
        clearUsers.setVisibility(View.GONE);
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
}