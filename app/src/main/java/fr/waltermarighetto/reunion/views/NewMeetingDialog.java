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

import android.widget.Button;
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
    TextView  meetingEndTime, mandatoryRoom, mandatoryName, meetingUsers,meetingDate, meetingTime;
    ImageView clearMeetingName, clearNewRoom, resetDate, resetTime, resetDuration;
    EditText meetingName,  meetingDuration ;
    LocalDateTime mStart;
    LocalDateTime mEnd;
    TimePicker newMeetingTimePicker;
    Spinner roomSpinner;
    ArrayAdapter<String> roomPicklistAdaptor;
    MultiPicker   usersPickerDialog;
    boolean[] currentUsersSelection;  // peut-on s'en passer ?



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //on crée la vue à afficher
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_new_meeting, null);
        // on prépare les données et listeners pour meeting Name, Date, Time, Duration, Room, Users
        manageName(v);
        manageDate(v);
        manageTime(v);
        manageDuration(v);
        manageRoom(v);
        manageUsers(v);

        resetNewMeeting();
//////////////////////////////////////////////////
        //on crée un bouton listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                // rajouter les actions
            }
        };
        ///////////////////////////////////////////////////////////////

        // on crée AlertDialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.new_meeting_title)
                .setView(v)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Cancel
                    }
                })
                .setNeutralButton(R.string.reset_all, null)
                .setCancelable(false);

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // Reset new meeting
                        resetNewMeeting();
                    }
                });

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
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
                            String s = "";
                            List<User> lUser = new ArrayList<User>();

                            if (usersPickerDialog != null) {
                                boolean[] selection = usersPickerDialog.getSelected();


                                for (int i = 0; i < selection.length; i++) {
                                    if (selection[i]) {
                                        s += InitData.getUsersGlobal().get(i).getUser().toString() + "\n";
                                        lUser.add(InitData.getUsersGlobal().get(i));
                                    }
                                }
                            }
                            currentMeeting.setUsers(lUser);
                            meetingUsers.setText(s);


                            InitData.addSortedMeeting(currentMeeting);
                            FilterMeetings.FilterMeetings();
                            MainActivity.mMeetingsAdapter.notifyDataSetChanged();
                            //resetNewMeeting();
                            //Dismiss once everything is OK.
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
        return dialog;
    }

    private void manageName(View view) {
        meetingName = view.findViewById(R.id.meeting_name);
        clearMeetingName = view.findViewById(R.id.clear_meeting_name);
        mandatoryName = view.findViewById(R.id.mandatoryName);

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
                    mandatoryName.setVisibility(View.GONE);
                    meetingName.setBackgroundColor(getResources().getColor(R.color.grey));
                } else {
                    clearMeetingName.setVisibility(View.GONE);
                    mandatoryName.setVisibility(View.VISIBLE);
                }
            }
        });

        clearMeetingName.setOnClickListener(fv -> {
            meetingName.setText("");
            mandatoryName.setVisibility(View.VISIBLE);
            meetingName.setHint(R.string.new_meeting_enter_name);
            meetingName.setBackgroundColor(getResources().getColor(R.color.grey));
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
                availableRooms();
                // on positionne le bouton de réinitialisation de la date
                resetDate.setVisibility(View.VISIBLE);
            }
        };

        meetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new DatePickerDialog(meetingDate.getContext(),
                            date,
                            newMeetingCalendar.get(Calendar.YEAR),
                            newMeetingCalendar.get(Calendar.MONTH),
                            newMeetingCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
            availableRooms();

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void manageTime(View view) {
        meetingTime = view.findViewById(R.id.meeting_start_time);
        resetTime = view.findViewById(R.id.reset_time);
        meetingEndTime = view.findViewById(R.id.meeting_end_time);

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
                availableRooms();
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

        meetingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                new TimePickerDialog(meetingTime.getContext(),
                            timeSetListener,
                            newMeetingTimePicker.getHour(),
                            newMeetingTimePicker.getMinute(), false).show();

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
            availableRooms();
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
                availableRooms();
            }
        });

        resetDuration.setOnClickListener(fv -> {
            meetingDuration.setText(getActivity().getString(R.string.meeting_average_duration));
            endTimeCalculation();
            availableRooms();
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


        roomSpinner.setSelection(0); // par défaut rien de sélectionné
        availableRooms();

        mandatoryRoom.setVisibility(View.VISIBLE);
        clearNewRoom.setVisibility(View.GONE);


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

    private void availableRooms() {
        String  roomSelectedName = getString(R.string.select_a_room);
        int roomSelectedId =0;
        if (roomSpinner.getSelectedItemPosition() !=0)
        roomSelectedName = roomPicklistAdaptor.getItem(roomSpinner.getSelectedItemPosition()).toString();

        roomPicklistAdaptor.clear();
        roomPicklistAdaptor.add(getActivity().getString(R.string.select_a_room));  // String vide pour dire qu'on ne sélectionne aucune room

        for (Room room : InitData.getRoomsGlobal()) {
            boolean keepRoom = true;
            if (mStart != null && mEnd != null) {
                for (Meeting meeting : InitData.getMeetingsGlobal()) {
                    if (meeting.getRoom().equals(room) && mStart.isBefore(meeting.getEnd())
                            && mEnd.isAfter(meeting.getStart())) {
                        keepRoom = false;
                        break;
                    }
                }
            }
            if (keepRoom == true) {
                roomPicklistAdaptor.add(room.getName());
                // on ne peut pas garder la sélection sur une salle qui n'est pas dans la liste
                if (room.getName().equals(roomSelectedName))
                    roomSelectedId = roomPicklistAdaptor.getCount() - 1;
            }
        }

        roomSpinner.setAdapter(roomPicklistAdaptor);
        roomSpinner.setSelection(roomSelectedId);
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

        //on crée un  listener pour traiter OK multiPicker
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                boolean[] selection = usersPickerDialog.getSelected();

                String s = "";
                for (int i = 0; i < selection.length; i++) {

                    if (selection[i]) {
                        s += InitData.getUsersGlobal().get(i).getUser().toString() + "\n";
                        currentUsersSelection[i] = true;
                    } else currentUsersSelection[i] = false;
                }
                if (s=="") s=getString(R.string.none_user);
                meetingUsers.setText(s);

            }
        };

        meetingUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               FragmentManager manager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                //             if (manager.findFragmentByTag("users") != null) return;
          //      usersPickerDialog = new MultiPicker().MultiPicker(usersNames, currentUsersSelection, getString(R.string.select_users), listener);
         //    usersPickerDialog.show(manager, "users");
            usersPickerDialog = MultiPicker.getInstance(usersNames,
                    currentUsersSelection, getString(R.string.select_users), listener, getContext());
        //       usersPickerDialog.show(manager, "users");

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void resetNewMeeting() {
        meetingName.setBackgroundColor(getResources().getColor(R.color.grey));
        meetingName.setText("");
        mandatoryName.setVisibility(View.VISIBLE);
        meetingName.setHint(R.string.new_meeting_enter_name);
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