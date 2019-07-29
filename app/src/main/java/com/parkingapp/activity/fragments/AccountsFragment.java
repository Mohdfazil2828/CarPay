package com.parkingapp.activity.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parkingapp.R;
import com.parkingapp.activity.activities.LoginActivity;
import com.parkingapp.activity.activities.OptionActivity;
import com.parkingapp.activity.activities.UserDashboardActivity;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountsFragment extends Fragment {

    private DBDetails sqlHelper; //db declaration
    private UserDetailsModal userDetailsModal;

    @BindView(R.id.accountname)
    TextView accountname;
    @BindView(R.id.accountemail)
    TextView accountemail;
    @BindView(R.id.accountmobile)
    TextView accountmobile;
    @BindView(R.id.addamount)
    ImageView addamount;
    @BindView(R.id.avlbalance)
    TextView avlbalance;


    String id, name, email, mobile;
    String userid;

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        ButterKnife.bind(AccountsFragment.this, view);
        sqlHelper = new DBDetails(getContext());


        userid = Utils.getUserId(getContext());
        getUserData();

        avlbalance.setText(String.valueOf(Utils.getAddAmount(getContext())));

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.qr_code, menu);
        inflater.inflate(R.menu.logout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scan_image:
                if (Utils.getAddAmount(getContext()) < 10) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Alert!");
                    alertDialog.setMessage("Please Top Up your Amount before enter!!!");
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialog.show();
                } else {
                    new IntentIntegrator(getActivity()).initiateScan();
                }
                break;

            case R.id.navigation_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alert");
                builder.setMessage("Do you really want to logout?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getContext(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        Utils.setIsLoginUser(getContext(), "0");
                        Utils.setIsLoggedIn(getContext(), "0");
                        Utils.setUserId(getContext(), "0");
                    }

                })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("No", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void getUserData() {

        userDetailsModal = sqlHelper.getUserData(userid);

        id = userDetailsModal.getUserId();
        name = userDetailsModal.getUserName();
        email = userDetailsModal.getUserEmail();
        mobile = userDetailsModal.getUserMobile();

        accountname.setText(name);
        accountemail.setText(email);
        accountmobile.setText(mobile);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.

        Log.e("TEST", "test");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.e("test", "" + result);
            } else {
                Log.e("scantest", "" + result);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.addamount)
    public void addamount() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_alert_box, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Top Up!");
        dialogBuilder.setMessage("Please Enter Your Amount:");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setIcon(R.drawable.ic_malaysian_ringgit);


        final EditText input = (EditText) dialogView.findViewById(R.id.enteravlbalance);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);


        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.e("value", "" + input.getText().toString());

                int sum;

                sum = Integer.parseInt(input.getText().toString()) + Utils.getAddAmount(getContext());

                Utils.setAddAmount(getContext(), sum);

                avlbalance.setText(String.valueOf(Utils.getAddAmount(getContext())));

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}
