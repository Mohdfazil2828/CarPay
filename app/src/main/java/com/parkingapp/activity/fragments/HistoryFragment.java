package com.parkingapp.activity.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parkingapp.R;
import com.parkingapp.activity.activities.adapter.HistoryRecyecler;
import com.parkingapp.activity.activities.loginmodal.UserLoginModal;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HistoryFragment extends Fragment {


    @BindView(R.id.recyeclerview)
    RecyclerView recyclerView;
   /* @BindView(R.id.mallnameTextView)
    TextView mallnametextview;*/


    private HistoryRecyecler myAdapter;


    private ArrayList<UserLoginModal> userLoginModals = new ArrayList<UserLoginModal>();
    private ArrayList<UserDetailsModal> userDetailsModals = new ArrayList<UserDetailsModal>();
    private ArrayList<UserDetailsModal> arrayMallName = new ArrayList<UserDetailsModal>();

    private DBDetails sqlHelper; //db declaration


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("History");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(HistoryFragment.this, view);
        sqlHelper = new DBDetails(getContext());// db initialization

        setUpRecyclerView();
/*
        getMallName();
*/

        addValuesArrayList();
        mallName();

        return view;

    }

/*    private void getMallName() {

        String adminMallId;

        userLoginModals = sqlHelper.getUserHistory(Utils.getUserId(getContext()));

        UserLoginModal modal = userLoginModals.get(0);
        adminMallId = modal.getAdmin_Id();

        arrayMallName = sqlHelper.getmallHistory(adminMallId);

        Log.e("mssg", "" + arrayMallName);

        UserDetailsModal modal1 = arrayMallName.get(0);

*//*
        mallnametextview.setText(modal1.getAdminName());
*//*

    }*/

    private void addValuesArrayList() {
        userLoginModals = sqlHelper.getUserHistory(Utils.getUserId(getContext()));
        myAdapter.updateData(userLoginModals);
    }

    private void mallName(){

        arrayMallName = sqlHelper.getmallHistory(Utils.getAdminId(getContext()));
        myAdapter.updateuserData(userDetailsModals);

    }

    private void setUpRecyclerView() {
        myAdapter = new HistoryRecyecler(HistoryFragment.this, userLoginModals);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myAdapter);
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
