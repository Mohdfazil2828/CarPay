package com.parkingapp.activity.activities.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parkingapp.R;
import com.parkingapp.activity.activities.loginmodal.UserLoginModal;
import com.parkingapp.activity.fragments.HistoryFragment;
import com.parkingapp.activity.modal.UserDetailsModal;

import java.util.ArrayList;

public class HistoryRecyecler extends RecyclerView.Adapter<HistoryRecyecler.MyViewHolder> {


    private HistoryFragment context;

    private ArrayList<UserLoginModal> userLoginModalArrayList = new ArrayList<UserLoginModal>();
    private ArrayList<UserDetailsModal> userDetailsModalArrayList = new ArrayList<UserDetailsModal>();

    public HistoryRecyecler(HistoryFragment context, ArrayList<UserLoginModal> userLoginModalArrayList) {

        this.userLoginModalArrayList = userLoginModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryRecyecler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyecler.MyViewHolder myViewHolder, final int position) {

        UserLoginModal modal = userLoginModalArrayList.get(position);

        final String userId = userLoginModalArrayList.get(position).getUser_Id();

        myViewHolder.mallnameTextView.setText(modal.getAdmin_Id());
        myViewHolder.timeinTextView.setText(modal.getTime_in());
        myViewHolder.timeoutTextView.setText(modal.getTime_out());

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

    public void updateuserData(ArrayList<UserDetailsModal> userDetailsModalArrayList) {
        this.userDetailsModalArrayList.clear();
        this.userDetailsModalArrayList = userDetailsModalArrayList;

        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mallnameTextView, timeinTextView, timeoutTextView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mallnameTextView = itemView.findViewById(R.id.mallnameTextView);
            timeinTextView = itemView.findViewById(R.id.timelogged);
            timeoutTextView = itemView.findViewById(R.id.timeloggedout);
        }
    }
}
