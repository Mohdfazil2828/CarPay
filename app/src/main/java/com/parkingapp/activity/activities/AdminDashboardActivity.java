package com.parkingapp.activity.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.parkingapp.R;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.fragments.TimeInFragment;
import com.parkingapp.activity.fragments.TimeOutFragment;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminDashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.admin_toolbar)
    Toolbar toolbar;

    String adminId;
    String mallName;
    private DBDetails sqlHelper;
    private UserDetailsModal userDetailsModal;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        adminId = Utils.getAdminId(getApplicationContext());

        mallName();
        getDetails();

        Bundle bundle = new Bundle();
        bundle.putString("adminId", adminId);
        fragment = new TimeInFragment();
        fragment.setArguments(bundle);
        switchFragment(fragment);

        //getting bottom admin_navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.admin_navigation);
        navigation.setOnNavigationItemSelectedListener(this);


    }

    private void mallName() {

        mallName = sqlHelper.getMallname(adminId);

    }

    private void getDetails() {

        userDetailsModal = sqlHelper.getData(adminId);
        if (userDetailsModal != null) {
            JSONObject adminData = new JSONObject();
            try {
                adminData.put("id", userDetailsModal.getAdminId());
                adminData.put("name", userDetailsModal.getAdminName());
                adminData.put("email", userDetailsModal.getAdminEmail());
                adminData.put("mobile", userDetailsModal.getAdminMobile());

                Log.e("test", "" + adminData);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void switchFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_time_in:
                switchFragment(new TimeInFragment());
                toolbar.setTitle(mallName + " - Time In");
                break;

            case R.id.navigation_time_out:
                switchFragment(new TimeOutFragment());
                toolbar.setTitle(mallName + " - Time Out");
                break;

            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Alert");
                builder.setMessage("Do you really want to logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(AdminDashboardActivity.this, AdminLoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Utils.setIsLoginAdmin(AdminDashboardActivity.this, "0");
                        Utils.setAdminId(AdminDashboardActivity.this, "0");
                    }

                })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you really want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
