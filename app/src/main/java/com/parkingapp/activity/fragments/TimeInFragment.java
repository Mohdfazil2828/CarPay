package com.parkingapp.activity.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.parkingapp.R;
import com.parkingapp.activity.database.DBDetails;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeInFragment extends Fragment {

    private DBDetails sqlHelper;
    String adminId;
    UserDetailsModal userDetailsModal;

    @BindView(R.id.time_in_qrimage)
    ImageView qrImageTimeIn;

    public TimeInFragment() {
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
        View view = inflater.inflate(R.layout.fragment_time_in, container, false);
        ButterKnife.bind(TimeInFragment.this, view);
        sqlHelper = new DBDetails(getContext());

        adminId = Utils.getAdminId(getContext());
        getDetails();


        return view;
    }

    private void getDetails() {

        userDetailsModal = sqlHelper.getData(adminId);
        if (userDetailsModal != null) {
            JSONObject adminData = new JSONObject();
            try {
                adminData.put("time", "0");
                adminData.put("id", userDetailsModal.getAdminId());
                adminData.put("name", userDetailsModal.getAdminName());
                adminData.put("email", userDetailsModal.getAdminEmail());
                adminData.put("mobile", userDetailsModal.getAdminMobile());


                Log.e("ttttt", "" + adminData);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(adminData.toString(), BarcodeFormat.QR_CODE, 400, 400);

                qrImageTimeIn.setImageBitmap(bitmap);
            } catch (Exception e) {

            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
