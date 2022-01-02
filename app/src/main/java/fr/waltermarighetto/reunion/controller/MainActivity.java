package fr.waltermarighetto.reunion.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.time.LocalDate;
import java.util.ArrayList;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.views.FilterMeetingsDialog;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;
import fr.waltermarighetto.reunion.views.NewMeetingDialog;

@SuppressLint({"NewApi", "ResourceType"})

public class MainActivity extends AppCompatActivity implements MainListener {
    // pour la fenêtre de dialogue du filtre sur les réunions
    private  MeetingsAdapter mMeetingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // chargement des données
        new InitData();

        // préparation du Recycler View pour affichage des meetings
        RecyclerView mRecycler = findViewById(R.id.meetings_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        new FilterMeetings();
        mMeetingsAdapter = new MeetingsAdapter(this, FilterMeetings.FilterMeetings());
        mRecycler.setAdapter(mMeetingsAdapter);

        // configure Refresh avec SwipeRefreshLayout
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                FilterMeetings.FilterMeetings();
                mMeetingsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Accès au dialogue de création d'un nouveau Meeting

        findViewById(R.id.new_meeting).setOnClickListener(fv -> {
                FragmentManager manager = getSupportFragmentManager();
                if (manager.findFragmentByTag("newmeeting") != null) return;

                NewMeetingDialog dialog = new NewMeetingDialog();
                dialog.show(manager, "newmeeting");
        });
    }

    // on met le bouton de filtre par date et/ou salle dans la barre d'actions (menu)

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.filter_meetings);

        View actionView = menuItem.getActionView();
        TextView textBadgeView = actionView.findViewById(R.id.filter_badge);
        String s = "";
        if (textBadgeView != null) {
            if (!FilterMeetings.getFilterRoom().isEmpty()) s = s + "R";
            if (FilterMeetings.getFilterDate() != null) s = s + "D";

            if (s.equals("")) {
                if (textBadgeView.getVisibility() != View.GONE)
                    textBadgeView.setVisibility(View.GONE);
            } else {
                textBadgeView.setText(s);
                if (textBadgeView.getVisibility() != View.VISIBLE)
                    textBadgeView.setVisibility(View.VISIBLE);
            }
        }

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // devrait être remplacé par un accès direct car il n'y a qu'une possibilité
        // mais structure conservée au cas où on rajoute un item de menu
        switch (item.getItemId()) {
            case R.id.filter_meetings:
              FragmentManager manager = getSupportFragmentManager();
                if (manager.findFragmentByTag("filterdialog") != null) return true;
              FilterMeetingsDialog filterMeetingsDialog = new FilterMeetingsDialog();
              Bundle args = new Bundle();
                String[] roomsNames = new String[FilterMeetings.getFilterRoom().size()];
                int i=0;
                while (i<FilterMeetings.getFilterRoom().size()) {
                    roomsNames[i] = FilterMeetings.getFilterRoom().get(i);
                    i++;
                }
              args.putStringArray("ROOMS", roomsNames);
                if (FilterMeetings.getFilterDate()== null)
                    args.putString("DATE",  "");

              else args.putString("DATE",  FilterMeetings.getFilterDate().toString());
                filterMeetingsDialog.setArguments(args);
                filterMeetingsDialog.show(manager, "filterdialog");

                return true;

        }
        return (super.onOptionsItemSelected(item));

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onFiltersUpdated(ArrayList<String> roomNames, LocalDate date) {
        FilterMeetings.setFilterDate(date);
        FilterMeetings.setFilterRoom(roomNames);
        FilterMeetings.FilterMeetings();
        mMeetingsAdapter.notifyDataSetChanged();
        invalidateOptionsMenu();

    }
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onNewMeetingUpdate(Meeting meeting) {
        InitData.addSortedMeeting(meeting);
        FilterMeetings.FilterMeetings();
        mMeetingsAdapter.notifyDataSetChanged();
    }

}


