package com.parkingapp.activity.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.parkingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentDoneActivity extends AppCompatActivity {

    @BindView(R.id.back_to_home)
    TextView backtohome;
    @BindView(R.id.toolbar_payment)
    Toolbar toolbarpayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_done);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarpayment);
        toolbarpayment.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @OnClick(R.id.back_to_home)
    public void backtohome() {
        Intent i = new Intent(PaymentDoneActivity.this, UserDashboardActivity.class);
        startActivity(i);
        finish();
    }
}
