package fr.waltermarighetto.reunion.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.waltermarighetto.reunion.R;
import fr.waltermarighetto.reunion.controller.FilterMeetings;
import fr.waltermarighetto.reunion.model.InitData;
import fr.waltermarighetto.reunion.model.Meeting;
import fr.waltermarighetto.reunion.model.Room;
import fr.waltermarighetto.reunion.model.User;

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.MeetingsViewHolder> {
    private  List<Meeting> aDataset;
    private Context mContext;
    Meeting meeting_to_delete;

     public MeetingsAdapter(Context context, List<Meeting> dataset ) {
        this.aDataset = dataset;
        this.mContext = context;
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        @SuppressLint("NotifyDataSetChanged")
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onClick(DialogInterface dialog, int id) {
            //OK
            for (int i = 0; i< InitData.getMeetingsGlobal().size(); i++ )
                if (meeting_to_delete.equals(InitData.getMeetingsGlobal().get(i))) {
                    InitData.getMeetingsGlobal().remove(i);
                    break;
                }
            FilterMeetings.FilterMeetings();
            MeetingsAdapter.this.notifyDataSetChanged();
        }
    };

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
    public void onBindViewHolder(@NonNull MeetingsViewHolder holder, final int position) {
        //       final YourItemViewHolder itemViewHolder = (YourItemViewHolder) holder;
        holder.mTextMeeting.setText(aDataset.get(position).getName());
        holder.mTextTime.setText(" - " + InitData.dtfTime.format(aDataset.get(position).getStart()) + " - ");
        holder.mTextRoom.setText(aDataset.get(position).getRoom().getName());
        String s = "";
        for (User user : aDataset.get(position).getUsers())
            s = s + user.getUser() + ", ";
        holder.mTextUsers.setText(s);

        Room ro = aDataset.get(position).getRoom();
        holder.mImageStatus.setColorFilter(ro.getColor());
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


            mImageRemove.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {

                    FragmentManager manager =  ((AppCompatActivity)mContext).getSupportFragmentManager();
                    DeleteMeetingDialog dialog = new DeleteMeetingDialog();
                    meeting_to_delete = FilterMeetings.getFilteredMeetings().get(getAdapterPosition());
                    dialog.setMeetingToDelete(meeting_to_delete);
                    dialog.setListener(listener);
                    dialog.show(manager, "delete");
                }

            });
        }
    }
}
