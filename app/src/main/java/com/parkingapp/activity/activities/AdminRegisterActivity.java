package com.parkingapp.activity.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingapp.R;
import com.parkingapp.activity.database.DBDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.parkingapp.R.id.btnAdminRegister;

public class AdminRegisterActivity extends AppCompatActivity {

    private DBDetails sqlHelper; //db declaration


    @BindView(R.id.adminFullName)
    EditText adminfullnameEditText;
    @BindView(R.id.adminEmail)
    EditText adminemailEditText;
    @BindView(R.id.adminMobile)
    EditText adminmobileEditText;
    @BindView(R.id.adminPassword)
    EditText adminpasswordEditText;
    @BindView(R.id.btnAdminRegister)
    Button adminregisterButton;
    @BindView(R.id.text_back_login)
    TextView backloginTextView;
    @BindView(R.id.linearLayout)
    LinearLayout layoutLinear;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization


    }

    @OnClick(btnAdminRegister)
    public void register() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(adminregisterButton.getWindowToken(), 0);

        if (adminfullnameEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter Your Full Name", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (adminfullnameEditText.getText().toString().length() < 3) {
            Snackbar.make(layoutLinear, "Full Name Must be greater than 4", Snackbar.LENGTH_LONG).show();
            return;
        }


        if (!adminemailEditText.getText().toString().matches(emailPattern)) {
            Snackbar.make(layoutLinear, "Enter a Valid Email", Snackbar.LENGTH_LONG).show();
            return;
        }


        if (adminmobileEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter Your Mobile Number", Snackbar.LENGTH_LONG).show();
            return;
        }


        if (adminmobileEditText.getText().toString().length() < 10) {
            Snackbar.make(layoutLinear, "Mobile Number must be 10 digits", Snackbar.LENGTH_LONG).show();
            return;

        }

        if (adminpasswordEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter password", Snackbar.LENGTH_LONG).show();
            return;
        }

        if ((adminpasswordEditText.getText().toString().length() < 8 || (adminpasswordEditText.getText().toString().length() > 16))) {
            Snackbar.make(layoutLinear, "must be greater than 8 less than 16", Snackbar.LENGTH_LONG).show();
            return;
        }

        dataSend();

    }

    private void dataSend() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(adminregisterButton.getWindowToken(), 0);


        String admin_name = adminfullnameEditText.getText().toString();
        String admin_email = adminemailEditText.getText().toString();
        String admin_mobile = adminmobileEditText.getText().toString();
        String admin_password = adminpasswordEditText.getText().toString();

        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Loading, Please Wait.");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        boolean dataTable;

        dataTable = sqlHelper.adminDetails(admin_name, admin_mobile, admin_email, admin_password);

        if (dataTable) {
            Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_LONG).show();
            dialog.dismiss();
            adminfullnameEditText.setText("");
            adminemailEditText.setText("");
            adminmobileEditText.setText("");
            adminpasswordEditText.setText("");

            Intent intent = new Intent(AdminRegisterActivity.this, AdminLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            Snackbar.make(layoutLinear, "Email already Exist", Snackbar.LENGTH_LONG).show();
            dialog.dismiss(); // will dismiss the dialog
        }
    }

    @OnClick(R.id.text_back_login)
    public void adminlogin() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(adminregisterButton.getWindowToken(), 0);


        Intent i = new Intent(AdminRegisterActivity.this, AdminLoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        adminlogin();

    }

}
