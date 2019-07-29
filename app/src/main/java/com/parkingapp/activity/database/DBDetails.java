package com.parkingapp.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parkingapp.activity.activities.loginmodal.UserLoginModal;
import com.parkingapp.activity.modal.UserDetailsModal;
import com.parkingapp.activity.utils.Utils;

import java.util.ArrayList;

public class DBDetails extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBPersonal.db";

    //create table details
    public static final String DETAILS_TABLE_NAME = "registration";
    public static final String DETAILS_COLUMN_ID = "id";
    public static final String DETAILS_COLUMN_NAME = "name";
    public static final String DETAILS_COLUMN_EMAIL = "email";
    public static final String DETAILS_COLUMN_MOBILE = "mobile";
    public static final String DETAILS_COLUMN_PASSWORD = "password";

    public static final String ADMIN_REGISTRATION_TABLE = "admin_registration_table";
    public static final String ADMIN_REGISTRATION_ID = "admin_id";
    public static final String ADMIN_REGISTRATION_NAME = "admin_name";
    public static final String ADMIN_REGISTRATION_EMAIL = "admin_email";
    public static final String ADMIN_REGISTRATION_MOBILE = "admin_mobile";
    public static final String ADMIN_REGISTRATION_PASSWORD = "admin_password";

    public static final String ENTER_EXIT_TABLE = "enter_exit_table";
    public static final String ENTER_EXIT_TABLE_ID = "id";
    public static final String ADMIN_ID = "admin_id";
    public static final String USER_ID = "user_id";
    public static final String TIME_IN = "time_in";
    public static final String TIME_OUT = "time_out";
    public static final String AMOUNT = "amount";
    public static final String TIME_FLAG = "time_flag";


    public DBDetails(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table registration" +
                        "(id integer primary key, name text,email text unique, mobile text, password text)"

        );

        db.execSQL(
                "create table admin_registration_table" +
                        "(id integer primary key, admin_name text,admin_email text unique, admin_mobile text, admin_password text)"

        );

        db.execSQL(
                "create table enter_exit_table" +
                        "(id integer primary key, admin_id integer, user_id integer, time_in integer, time_out integer, amount integer, time_flag integer)"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS registration");
        db.execSQL("DROP TABLE IF EXISTS admin_registration_table");
        db.execSQL("DROP TABLE IF EXISTS enter_exit_table");

        onCreate(db);
    }

    public boolean insertDetails(String name, String mobile, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("mobile", mobile);
        contentValues.put("email", email);
        contentValues.put("password", password);

        long details = db.insert("registration", null, contentValues);

        boolean users;

        if (details != -1) {
            users = true;

        } else {
            users = false;
        }
        return users;
    }

    public boolean checkUser(Context context, String emailtext, String passwordtext) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + DBDetails.DETAILS_TABLE_NAME + " where " + DETAILS_COLUMN_EMAIL + "=?", new String[]{emailtext});


        boolean flag = false;

        if (res.moveToFirst()) {

            String idDB = res.getString(0);
            String emaiLDB = res.getString(2);
            String passwordDB = res.getString(4);

            if (emailtext.equals(emaiLDB)) {
                if (passwordtext.equals(passwordDB)) {
                    flag = true;
                    Utils.setUserId(context, idDB);
                } else {
                    flag = false;
                    Utils.setUserId(context, "0");
                }
            }

        }

        return flag;
    }

    public UserDetailsModal getUserData(String userId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from registration where id = " + userId, null);


        UserDetailsModal userDetailsModal = null;

        if (res.moveToFirst()) {
            userDetailsModal = new UserDetailsModal();
            userDetailsModal.setUserId(res.getString(0));
            userDetailsModal.setUserName(res.getString(1));
            userDetailsModal.setUserEmail(res.getString(2));
            userDetailsModal.setUserMobile(res.getString(3));


        }


        return userDetailsModal;
    }

    public boolean adminDetails(String admin_name, String admin_mobile, String admin_email, String admin_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("admin_name", admin_name);
        contentValues.put("admin_email", admin_email);
        contentValues.put("admin_mobile", admin_mobile);
        contentValues.put("admin_password", admin_password);

        long details = db.insert("admin_registration_table", null, contentValues);

        boolean admin;

        if (details != -1) {
            admin = true;

        } else {
            admin = false;
        }
        return admin;
    }

    public boolean checkAdmin(Context context, String adminemailtext, String adminpasswordtext) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + DBDetails.ADMIN_REGISTRATION_TABLE + " where " + ADMIN_REGISTRATION_EMAIL + "=?", new String[]{adminemailtext});

        boolean data = false;

        if (res.moveToFirst()) {

            String idDB = res.getString(0);
            String emailDB = res.getString(2);
            String passwordDB = res.getString(4);

            if (adminemailtext.equals(emailDB)) {
                if (adminpasswordtext.equals(passwordDB)) {
                    data = true;
                    Utils.setAdminId(context, idDB);
                } else {
                    data = false;
                    Utils.setAdminId(context, "0");
                }
            }

        }

        return data;
    }

    public UserDetailsModal getData(String adminId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from admin_registration_table where id = " + adminId, null);


        UserDetailsModal userDetailsModal = null;

        if (res.moveToFirst()) {
            userDetailsModal = new UserDetailsModal();
            userDetailsModal.setAdminId(res.getString(0));
            userDetailsModal.setAdminName(res.getString(1));
            userDetailsModal.setAdminEmail(res.getString(2));
            userDetailsModal.setAdminMobile(res.getString(3));


        }


        return userDetailsModal;
    }

    public String getMallname(String adminid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select admin_name from admin_registration_table where id = " + adminid, null);


        String A = "";
        if (res.moveToFirst()) {

            A = res.getString(0);
        }

        return A;
    }

    public int entrydata(String admin_id, String user_id, String time_in, String time_flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("admin_id", admin_id);
        contentValues.put("user_id", user_id);
        contentValues.put("time_in", time_in);
        contentValues.put("time_flag", time_flag);

        int a;

        long details = db.insert("enter_exit_table", null, contentValues);


        if (details != -1) {
            a = (int) details;
        } else {
            a = 0;
        }
        return a;
    }

    public int exitdata(String id, String time_out, String amount, String time_flag) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("time_out", time_out);
        contentValues.put("amount", amount);
        contentValues.put("time_flag", time_flag);

        long details = db.update("enter_exit_table", contentValues, "id = ?", new String[]{(id)});

        int a;

        Log.e("yes", "" + details);
        if (details != -1) {
            a = (int) details;
        } else {
            a = 0;
        }
        return a;
    }

    public ArrayList<UserLoginModal> getadminid(String Id) {

        ArrayList<UserLoginModal> arrayList = new ArrayList<UserLoginModal>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT admin_id, time_in FROM enter_exit_table WHERE time_flag = 1 and user_id = ? ", new String[]{(Id)});


        if (res.moveToFirst()) {

            do {
                UserLoginModal userLoginModal = new UserLoginModal();
                userLoginModal.setAdmin_Id(res.getString(0));
                userLoginModal.setTime_in(res.getString(1));

                arrayList.add(userLoginModal);

            }
            while (res.moveToNext());
        }

        return arrayList;
    }

    public ArrayList<UserDetailsModal> getMallName(String Id) {

        Log.e("echo", "" + Id);

        ArrayList<UserDetailsModal> arrayListmall = new ArrayList<UserDetailsModal>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT admin_name, admin_email, admin_mobile FROM admin_registration_table WHERE id = ? ", new String[]{(Id)});

        Log.e("eeee", "" + res);

        if (res.moveToFirst()) {
            Log.e("rerer", "test");
            do {
                UserDetailsModal userDetailsModal = new UserDetailsModal();
                userDetailsModal.setAdminName(res.getString(0));
                userDetailsModal.setAdminEmail(res.getString(1));
                userDetailsModal.setAdminMobile(res.getString(2));


                arrayListmall.add(userDetailsModal);
                Log.e("array", "" + arrayListmall);
            }
            while (res.moveToNext());
        }

        return arrayListmall;
    }

    public ArrayList<UserLoginModal> getUserTimeIn(String id) {

        ArrayList<UserLoginModal> arrayList = new ArrayList<UserLoginModal>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT time_in, time_out FROM enter_exit_table WHERE id = ?", new String[]{(id)});


        if (res.moveToFirst()) {

            do {
                UserLoginModal userLoginModal = new UserLoginModal();
                userLoginModal.setTime_in(res.getString(0));
                userLoginModal.setTime_out(res.getString(1));

                arrayList.add(userLoginModal);

            }
            while (res.moveToNext());
        }

        return arrayList;
    }

    public ArrayList<UserLoginModal> getUserHistory(String id) {

        ArrayList<UserLoginModal> arrayList = new ArrayList<UserLoginModal>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT admin_id, time_in, time_out FROM enter_exit_table WHERE time_flag = 0 and user_id = ? ", new String[]{(id)});


        if (res.moveToFirst()) {

            do {
                UserLoginModal userLoginModal = new UserLoginModal();
                userLoginModal.setAdmin_Id(res.getString(0));
                userLoginModal.setTime_in(res.getString(1));
                userLoginModal.setTime_out(res.getString(2));

                arrayList.add(userLoginModal);

            }
            while (res.moveToNext());
        }

        return arrayList;
    }

    public ArrayList<UserDetailsModal> getmallHistory(String id) {

        ArrayList<UserDetailsModal> arrayList = new ArrayList<UserDetailsModal>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT admin_name FROM admin_registration_table WHERE id = ?", new String[]{(id)});


        if (res.moveToFirst()) {

            do {
                UserDetailsModal userdetailsModal = new UserDetailsModal();
                userdetailsModal.setUserId(res.getString(0));

                arrayList.add(userdetailsModal);

            }
            while (res.moveToNext());
        }

        return arrayList;
    }
}
