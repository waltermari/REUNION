package fr.waltermarighetto.reunion.controller;

import androidx.annotation.NonNull;
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
import android.view.View;

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
/////// A REVOIR §§§§
        mRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   //           new  DeleteMeetingDialog( this, mMeetingsAdapter.getAdapterPosition());
                mMeetingsAdapter.notifyDataSetChanged();
            }
        });
 /////// Fin A REVOIR

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

// Accès au dialogue de création d'un nouveau Meeting

        FloatingActionButton floatingNewMeeting = findViewById(R.id.new_meeting);
        floatingNewMeeting.setOnClickListener(view -> {
                    try {
                        new NewMeetingDialog(this).show();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
    //                new NewMeetingDialog(this).show();

                    // trop tôt il faut l'activer sur OK                   initRecyclerForMeetings();


                }
        );
    }


    // on met le bouton de filtre par date et/ou salle dans la barre d'actions

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        new FilterMeetingsDialog(this);
        return true;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // devrait être remplacé par un accès direct car il n'y a qu'une possibilité
        // mais structure conservée au cas où on rajoute un item de menu
        switch (item.getItemId()) {
            case R.id.filter_meetings:

                FilterMeetingsDialog.filterMeetingsDialog.show();

                return (true);

        }
        return (super.onOptionsItemSelected(item));

    }
}





