package fr.waltermarighetto.reunion.views;

// import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.MeetingsViewHolder> {
    private final List<Meeting> aDataset; 


    // Tableau de couleur des salles
    int[] roomColors = {Color.BLUE, Color.GREEN, Color.BLACK, Color.RED,
            Color.CYAN, Color.GRAY, Color.MAGENTA, Color.YELLOW, Color.DKGRAY, Color.LTGRAY};


    public MeetingsAdapter(List<Meeting> dataset ) {
        this.aDataset = dataset;
    }


    @NonNull
    @Override
    public MeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_meetings, parent, false);
        MeetingsViewHolder meetingsViewHolder = new MeetingsViewHolder(view);
        return meetingsViewHolder;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MeetingsViewHolder holder, int position) {

        holder.mTextMeeting.setText(aDataset.get(position).getName());
        holder.mTextTime.setText(" - " + InitData.dtfTime.format(aDataset.get(position).getStart()) + " - ");
        holder.mTextRoom.setText(aDataset.get(position).getRoom().getName());
        String s = "";
        for (User user : aDataset.get(position).getUsers())
            s = s + user.getUser() + ", ";
        holder.mTextUsers.setText(s);

        Room ro = aDataset.get(position).getRoom();

        for (int i = 0; i < InitData.mRoomsGlobal.size(); i++) {
            if (ro.getName().toString().equals( InitData.mRoomsGlobal.get(i).getName().toString())) {
            holder.mImageStatus.setBackgroundColor(roomColors[i]);
            break;}
        }

  //      holder.mImageStatus.getDrawable();
        holder.mImageRemove.forceLayout();

    }
        @Override
        public int getItemCount () {
            return aDataset.size();
        }

        public class MeetingsViewHolder extends RecyclerView.ViewHolder {
            ImageView mImageStatus;
            ImageView mImageRemove;
            TextView mTextMeeting;
            TextView mTextTime;
            TextView mTextRoom;
            TextView mTextUsers;

            public MeetingsViewHolder(@NonNull View itemView) {
                super(itemView);
                mImageStatus = itemView.findViewById(R.id.image_status);
                mImageRemove = itemView.findViewById(R.id.image_remove);
                mTextMeeting = itemView.findViewById(R.id.item_meeting);
                mTextTime = itemView.findViewById(R.id.item_time);
                mTextRoom = itemView.findViewById(R.id.item_room);
                mTextUsers = itemView.findViewById(R.id.item_users);
            }
        }

}
