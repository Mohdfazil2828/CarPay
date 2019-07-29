package com.parkingapp.activity.activities.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parkingapp.R;
import com.parkingapp.activity.activities.loginmodal.UserLoginModal;
import com.parkingapp.activity.fragments.NotificationsFragment;

import java.util.ArrayList;

public class NotifacationRecyecler extends RecyclerView.Adapter<NotifacationRecyecler.MyViewHolder> {

    private NotificationsFragment context;

    private ArrayList<UserLoginModal> userLoginModalArrayList = new ArrayList<UserLoginModal>();



    public NotifacationRecyecler(NotificationsFragment context, ArrayList<UserLoginModal> userLoginModalArrayList) {

        this.userLoginModalArrayList = userLoginModalArrayList;
        this.context = context;


    }


    @NonNull
    @Override
    public NotifacationRecyecler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notifications_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotifacationRecyecler.MyViewHolder myViewHolder, final int position) {

        UserLoginModal modal = userLoginModalArrayList.get(position);

        final String userId = userLoginModalArrayList.get(position).getUser_Id();

        myViewHolder.timeloggedTextView.setText(modal.getTime_in());
        myViewHolder.timeloggedoutTextView.setText(modal.getTime_out());


    }

    @Override
    public int getItemCount() {
        return userLoginModalArrayList.size();
    }

    public void updateData(ArrayList<UserLoginModal> userLoginModalArrayList) {
        this.userLoginModalArrayList.clear();
        this.userLoginModalArrayList = userLoginModalArrayList;

        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView timeloggedTextView, timeloggedoutTextView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeloggedTextView = itemView.findViewById(R.id.timelogged);
            timeloggedoutTextView = itemView.findViewById(R.id.timeloggedout);
        }
    }
}
