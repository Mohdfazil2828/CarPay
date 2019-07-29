package com.parkingapp.activity.activities;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parkingapp.R;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.fragments.AccountsFragment;
import com.parkingapp.activity.fragments.HistoryFragment;
import com.parkingapp.activity.fragments.NotificationsFragment;
import com.parkingapp.activity.fragments.TimeInFragment;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @BindView(R.id.constrainrLayout)
    ConstraintLayout constraintLayout;

    String userId;
    String adminId;
    private DBDetails sqlHelper;
    private UserDetailsModal userDetailsModal;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        userId = Utils.getUserId(getApplicationContext());
        adminId = Utils.getAdminId(getApplicationContext());

        getUserData();

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        fragment = new TimeInFragment();
        fragment.setArguments(bundle);
        switchFragment(fragment);

        BottomNavigationView navigation = findViewById(R.id.user_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        switchFragment(new AccountsFragment());

    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.qr_code, menu);
        return true;
    }*/

    private void getUserData() {

        userDetailsModal = sqlHelper.getData(userId);
        if (userDetailsModal != null) {
            JSONObject userData = new JSONObject();
            try {
                userData.put("id", userDetailsModal.getUserId());
                userData.put("name", userDetailsModal.getUserName());
                userData.put("email", userDetailsModal.getUserEmail());
                userData.put("mobile", userDetailsModal.getUserMobile());


                Log.e("test", "" + userData);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }


    private void switchFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.user_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_accounts:
                switchFragment(new AccountsFragment());
                toolbar.setTitle("Accounts");
                break;

            case R.id.navigation_notification:
                switchFragment(new NotificationsFragment());
                toolbar.setTitle("Notification");
                break;

            case R.id.navigation_history:
                switchFragment(new HistoryFragment());
                toolbar.setTitle("History");
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Log.e("TEST", "test");

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Snackbar.make(constraintLayout, "Something Went Wrong", Snackbar.LENGTH_SHORT).show();
                Log.e("test", "" + result);
            } else {
                String time = "", id = "", name = "", email = "", mobile = "";

                Log.e("test", "" + result);
                try {
                    JSONObject jsonObject = new JSONObject("" + result.getContents());

                    time = jsonObject.getString("time");
                    id = jsonObject.getString("id");
                    name = jsonObject.getString("name");
                    email = jsonObject.getString("email");
                    mobile = jsonObject.getString("mobile");

                    if (time != null) {

                        if (time.equals("0")) {
                            Intent intent = new Intent(UserDashboardActivity.this, UserTimeStart.class);
                            intent.putExtra("time", "" + time);
                            intent.putExtra("id", "" + id);
                            intent.putExtra("name", "" + name);
                            intent.putExtra("email", "" + email);
                            intent.putExtra("mobile", "" + mobile);
                            startActivity(intent);
                            finish();

                        } else if (time.equals("1")) {
                            Snackbar.make(constraintLayout, "You have scanned Wrong QR code!", Snackbar.LENGTH_SHORT).show();

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("catch", "" + e.getMessage());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
