package com.parkingapp.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parkingapp.R;
import com.parkingapp.activity.activities.adapter.NotifacationRecyecler;
import com.parkingapp.activity.activities.loginmodal.UserLoginModal;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationsFragment extends Fragment {

    @BindView(R.id.recyeclerview)
    RecyclerView recyclerView;


    private NotifacationRecyecler mAdapter;

    private ArrayList<UserLoginModal> userLoginModals = new ArrayList<UserLoginModal>();
    private DBDetails sqlHelper; //db declaration


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(NotificationsFragment.this, view);
        sqlHelper = new DBDetails(getContext());// db initialization

        setUpRecyclerView();
        addValuesArrayList();

        return view;

    }


    private void addValuesArrayList() {
        userLoginModals = sqlHelper.getUserTimeIn(Utils.getUserId(getContext()));
        mAdapter.updateData(userLoginModals);
    }

    private void setUpRecyclerView() {
        mAdapter = new NotifacationRecyecler(NotificationsFragment.this, userLoginModals);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
