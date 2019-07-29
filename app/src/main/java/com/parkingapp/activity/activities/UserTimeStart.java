package com.parkingapp.activity.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parkingapp.R;
import com.parkingapp.activity.activities.loginmodal.UserLoginModal;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserTimeStart extends AppCompatActivity {

    private DBDetails sqlHelper; //db declaration
    private ArrayList<UserLoginModal> arrayListadmin = new ArrayList<UserLoginModal>();
    private ArrayList<UserDetailsModal> arrayListmallname = new ArrayList<UserDetailsModal>();

    String id, admin_id, user_id, time_in, time_out;
    String adminId;
    String userId;


    @BindView(R.id.my_toolbar_user)
    Toolbar toolbar;
    @BindView(R.id.textname)
    TextView textname;
    @BindView(R.id.timeloggedout)
    TextView timeloggedout;
    @BindView(R.id.loggedout)
    TextView loggedlout;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.feeamount)
    TextView feeamount;
    @BindView(R.id.textemail)
    TextView textemail;
    @BindView(R.id.textmobile)
    TextView textmobile;
    @BindView(R.id.timelogin)
    TextView timelogin;
    @BindView(R.id.timelogged)
    TextView timelogged;
    @BindView(R.id.btnconfirm)
    Button btnconfirm;
    @BindView(R.id.btnexit)
    Button btnexit;
    @BindView(R.id.btnpaynow)
    Button btnpaynow;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_time_start);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization

        adminId = Utils.getAdminId(getApplicationContext());
        userId = Utils.getUserId(getApplicationContext());

        getIntentValues(getIntent());

        if (Utils.getIsLoggedIn(UserTimeStart.this).equals("1")) {
            getadminid();

            btnconfirm.setVisibility(View.GONE);
            timelogin.setVisibility(View.VISIBLE);
            btnexit.setVisibility(View.VISIBLE);

        } else if (Utils.getIsLoggedIn(UserTimeStart.this).equals("0")) {
            btnconfirm.setVisibility(View.VISIBLE);
        }

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.getIsLoggedIn(UserTimeStart.this).equals("1")) {
                    finish();
                } else {
                    Intent i = new Intent(UserTimeStart.this, UserDashboardActivity.class);
                    startActivity(i);
                }
            }
        });

    }


    private void getIntentValues(Intent intent) {

        String time = intent.getStringExtra("time");
        admin_id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String mobile = intent.getStringExtra("mobile");


        textname.setText(name);
        textemail.setText(email);
        textmobile.setText(mobile);

        timelogin.setVisibility(View.GONE);
        btnexit.setVisibility(View.GONE);
        loggedlout.setVisibility(View.GONE);
        amount.setVisibility(View.GONE);
        btnpaynow.setVisibility(View.GONE);

    }

    private void getadminid() {

        String adminmallid;

        arrayListadmin = sqlHelper.getadminid(Utils.getUserId(getApplicationContext()));

        UserLoginModal modal = arrayListadmin.get(0);
        adminmallid = modal.getAdmin_Id();

        timelogged.setText(modal.getTime_in());


        arrayListmallname = sqlHelper.getMallName(adminmallid);

        Log.e("mssg", "" + arrayListmallname);

        UserDetailsModal modal1 = arrayListmallname.get(0);

        textname.setText(modal1.getAdminName());
        textemail.setText(modal1.getAdminEmail());
        textmobile.setText(modal1.getAdminMobile());

        Utils.setIsLoggedIn(UserTimeStart.this, "1");


    }


    @OnClick(R.id.btnconfirm)
    public void btnconfirm() {

        Date current = new Date();
        SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        final String date = Format.format(current);
        Log.e("time", "" + date);

        time_in = date;
        user_id = Utils.getUserId(getApplicationContext());


        AlertDialog alertDialog = new AlertDialog.Builder(UserTimeStart.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("You have Successfully Logged in to " + textname.getText().toString() + " mall at " + date);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                timelogged.setText(date);

                int insertTable;
                insertTable = sqlHelper.entrydata(admin_id, user_id, time_in, "1");
                if (insertTable != 0) {

                    timelogin.setVisibility(View.VISIBLE);
                    btnconfirm.setVisibility(View.GONE);

                    btnexit.setVisibility(View.VISIBLE);

                    Utils.setIsLoggedIn(UserTimeStart.this, "1");


                }
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.btnexit)
    public void btnexit() {

        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Date current = new Date();
        SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        final String date = Format.format(current);
        Log.e("time", "" + date);

        time_out = date;

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Snackbar.make(linearLayout, "Something Went Wrong", Snackbar.LENGTH_SHORT).show();
            } else {
                String time = "";

                Log.e("test", "" + result);
                try {
                    JSONObject jsonObject = new JSONObject("" + result.getContents());

                    time = jsonObject.getString("time");

                    if (time != null) {

                        if (time.equals("1")) {

                            timeloggedout.setText(date);
                            feeamount.setText(String.valueOf(5));

                            int updateTable;
                            updateTable = sqlHelper.exitdata(userId, time_out, "5", "0");
                            if (updateTable != 0) {

                                Log.e("data", "" + updateTable);

                                timelogin.setVisibility(View.VISIBLE);
                                btnconfirm.setVisibility(View.GONE);

                                btnexit.setVisibility(View.GONE);
                                loggedlout.setVisibility(View.VISIBLE);
                                amount.setVisibility(View.VISIBLE);

                                btnpaynow.setVisibility(View.VISIBLE);

                            }

                        } else if (time.equals("0")) {
                            Snackbar.make(linearLayout, "You have scanned Wrong QR code!", Snackbar.LENGTH_SHORT).show();

                            loggedlout.setVisibility(View.GONE);
                            amount.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("catch", "" + e.getMessage());
                }
            }
        }

    }

    @OnClick(R.id.btnpaynow)
    public void btnpaynow() {

        int minus;

        minus = Utils.getAddAmount(getApplicationContext()) - 5;

        Utils.setAddAmount(getApplicationContext(), minus);

        Intent i = new Intent(UserTimeStart.this, PaymentDoneActivity.class);
        startActivity(i);
        Utils.setIsLoggedIn(UserTimeStart.this, "0");
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}