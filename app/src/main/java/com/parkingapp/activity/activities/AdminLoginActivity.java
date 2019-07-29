package com.parkingapp.activity.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parkingapp.R;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminLoginActivity extends AppCompatActivity {

    private DBDetails sqlHelper; //db declaration

    @BindView(R.id.adminEmail)
    EditText adminemailEditText;
    @BindView(R.id.adminPassword)
    EditText adminpasswordEditText;
    @BindView(R.id.btn_admin_login)
    Button adminloginButton;
    @BindView(R.id.btnAdminRegister)
    Button adminregisterButton;
    @BindView(R.id.linearLayout)
    LinearLayout layoutLinear;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization
    }

    @OnClick(R.id.btn_admin_login)
    public void adminlogin() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(adminloginButton.getWindowToken(), 0);


        if (!adminemailEditText.getText().toString().matches(emailPattern)) {
            Snackbar.make(layoutLinear, "Enter a Valid Email", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (adminpasswordEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter password", Snackbar.LENGTH_LONG).show();
            return;
        }

        if ((adminpasswordEditText.getText().toString().length() < 8 || (adminpasswordEditText.getText().toString().length() > 16))) {
            Snackbar.make(layoutLinear, "Password must be greater than 8 and less than 16", Snackbar.LENGTH_LONG).show();
            return;
        }

        adminloginCheck();

    }

    private void adminloginCheck() {


        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Loading, Please Wait.");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        if (sqlHelper.checkAdmin(getApplicationContext(), adminemailEditText.getText().toString().trim(), adminpasswordEditText.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
            Intent i = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            Utils.setIsLoginAdmin(AdminLoginActivity.this, "1");

        } else {
            Snackbar.make(layoutLinear, "Invalid Credentials", Snackbar.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }


    public void onBackPressed() {
        Intent i = new Intent(AdminLoginActivity.this, OptionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @OnClick(R.id.btnAdminRegister)
    public void adminregister() {
        Intent i = new Intent(AdminLoginActivity.this, AdminRegisterActivity.class);
        startActivity(i);

    }

}
