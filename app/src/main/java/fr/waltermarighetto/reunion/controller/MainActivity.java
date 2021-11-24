package fr.waltermarighetto.reunion.controller;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;



import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.views.FilterMeetingsDialog;
import fr.waltermarighetto.reunion.views.MeetingsAdapter;
import fr.waltermarighetto.reunion.views.NewMeetingDialog;

@SuppressLint({"NewApi", "ResourceType"})

public class MainActivity extends AppCompatActivity {

    // pour la fenêtre de dialogue du filtre sur les réunions
    public static MeetingsAdapter mMeetingsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // chargement des données
        new InitData();

        // préparation du Recycler View pour affichage des meetings
        manageMeetingsDisplay();

        // préparation accès création nouveau Meeting par FloatingActionButton
        manageNewMeetingAccess();

        // configure Refresh avec SwipeRefreshLayout
        refreshMeetingsDisplay();
    }
    private void manageMeetingsDisplay() {
        RecyclerView mRecycler = findViewById(R.id.meetings_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mMeetingsAdapter = new MeetingsAdapter(this, new FilterMeetings().FilterMeetings());
        mRecycler.setAdapter(mMeetingsAdapter);
    }

    private void manageNewMeetingAccess() {

        // Accès au dialogue de création d'un nouveau Meeting

        findViewById(R.id.new_meeting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getSupportFragmentManager();
                if (manager.findFragmentByTag("dialog") != null) {
          //          manager.findFragmentByTag("dialog").setReenterTransition();
                }
                    NewMeetingDialog dialog = new NewMeetingDialog();
                dialog.show(manager, "dialog");
            }
        });
    }

    private void refreshMeetingsDisplay() {
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FilterMeetings.FilterMeetings();
                mMeetingsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    // on met le bouton de filtre par date et/ou salle dans la barre d'actions (menu)


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // devrait être remplacé par un accès direct car il n'y a qu'une possibilité
        // mais structure conservée au cas où on rajoute un item de menu
        switch (item.getItemId()) {
            case R.id.filter_meetings:
                FragmentManager manager = getSupportFragmentManager();
             FilterMeetingsDialog filterMeetingsDialog =  new FilterMeetingsDialog();
                filterMeetingsDialog.show(manager, "Dialog");
                return true;

        }
        return (super.onOptionsItemSelected(item));

    }
}



