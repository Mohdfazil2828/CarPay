package com.parkingapp.activity.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.parkingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OptionActivity extends AppCompatActivity {

    @BindView(R.id.btn_user_login)
    Button userLogin;
    @BindView(R.id.btn_admin_login)
    Button adminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_user_login)
    public void userLogin() {
        Intent i = new Intent(OptionActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.btn_admin_login)
    public void adminLogin() {
        Intent i = new Intent(OptionActivity.this, AdminLoginActivity.class);
        startActivity(i);
        finish();
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
