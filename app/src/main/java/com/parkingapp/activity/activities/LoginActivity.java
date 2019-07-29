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

public class LoginActivity extends AppCompatActivity {

    private DBDetails sqlHelper; //db declaration

    @BindView(R.id.etEmail)
    EditText emailEditText;
    @BindView(R.id.etLoginPassword)
    EditText passwordEditText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.btnRegister)
    Button registerButton;
    @BindView(R.id.linearLayout)
    LinearLayout layoutLinear;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sqlHelper = new DBDetails(this);// db initialization


    }

    @OnClick(R.id.btn_login)
    public void login() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(loginButton.getWindowToken(), 0);

        if (!emailEditText.getText().toString().matches(emailPattern)) {
            Snackbar.make(layoutLinear, "Enter a Valid Email", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (passwordEditText.getText().toString().equals("")) {
            Snackbar.make(layoutLinear, "Enter password", Snackbar.LENGTH_LONG).show();
            return;
        }

        if ((passwordEditText.getText().toString().length() < 8 || (passwordEditText.getText().toString().length() > 16))) {
            Snackbar.make(layoutLinear, "Password must be greater than 8 and less than 16", Snackbar.LENGTH_LONG).show();
            return;
        }

        loginCheck();

    }

    private void loginCheck() {


        final ProgressDialog dialog = new ProgressDialog(this);


        if (sqlHelper.checkUser(getApplicationContext(), emailEditText.getText().toString().trim(), passwordEditText.getText().toString().trim())) {

            dialog.setMessage("Loading, Please Wait.");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.dismiss();

            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
            Intent i = new Intent(LoginActivity.this, UserDashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.setIsLoginUser(LoginActivity.this, "1");
            startActivity(i);

        } else {
            Snackbar.make(layoutLinear, "Invalid Credentials", Snackbar.LENGTH_SHORT).show();
        }
    }


    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, OptionActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @OnClick(R.id.btnRegister)
    public void register() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }
}
