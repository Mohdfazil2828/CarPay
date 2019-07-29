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
import android.widget.TextView;
import android.widget.Toast;

import com.parkingapp.R;
import com.parkingapp.activity.database.DBDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.parkingapp.R.id.btnRegister;

public class RegisterActivity extends AppCompatActivity {

    private DBDetails sqlHelper; //db declaration


    @BindView(R.id.etFullName)
    EditText fullnameEditText;
    @BindView(R.id.etEmail)
    EditText emailEditText;
    @BindView(R.id.etMobile)
    EditText mobileEditText;
    @BindView(R.id.etPassword)
    EditText passwordEditText;
    @BindView(btnRegister)
    Button registerButton;
    @BindView(R.id.text_back_login)
    TextView backloginTextView;
    @BindView(R.id.linearLayout)
    LinearLayout layoutLinear;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization

    }

    @OnClick(btnRegister)
    public void register() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(registerButton.getWindowToken(), 0);

        if (fullnameEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter Your Full Name", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (fullnameEditText.getText().toString().length() < 3) {
            Snackbar.make(layoutLinear, "Full Name Must be greater than 4", Snackbar.LENGTH_LONG).show();
            return;
        }


        if (!emailEditText.getText().toString().matches(emailPattern)) {
            Snackbar.make(layoutLinear, "Enter a Valid Email", Snackbar.LENGTH_LONG).show();
            return;
        }


        if (mobileEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter Your Mobile Number", Snackbar.LENGTH_LONG).show();
            return;
        }


        if (mobileEditText.getText().toString().length() < 10) {
            Snackbar.make(layoutLinear, "Mobile Number must be 10 digits", Snackbar.LENGTH_LONG).show();
            return;

        }

        if (passwordEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter password", Snackbar.LENGTH_LONG).show();
            return;
        }

        if ((passwordEditText.getText().toString().length() < 8 || (passwordEditText.getText().toString().length() > 16))) {
            Snackbar.make(layoutLinear, "must be greater than 8 less than 16", Snackbar.LENGTH_LONG).show();
            return;
        }

        senDataToServer();

    }

    private void senDataToServer() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(registerButton.getWindowToken(), 0);


        String name = fullnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String mobile = mobileEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Loading, Please Wait.");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.dismiss();

        boolean insertTable;

        insertTable = sqlHelper.insertDetails(name, mobile, email, password);

        if (insertTable) {
            Toast.makeText(getApplicationContext(), "Register Successful", Toast.LENGTH_LONG).show();
            dialog.dismiss();
            fullnameEditText.setText("");
            emailEditText.setText("");
            mobileEditText.setText("");
            passwordEditText.setText("");

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            Snackbar.make(layoutLinear, "Email already Exist", Snackbar.LENGTH_LONG).show();
            dialog.dismiss(); // will dismiss the dialog
        }

    }


    @OnClick(R.id.text_back_login)
    public void login() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(registerButton.getWindowToken(), 0);


        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        login();

    }


}




