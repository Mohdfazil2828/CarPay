package com.parkingapp.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    private static final String PREFERENCE_NAME = "parkingapp";

    private static final String IS_LOGIN_ADMIN = "isloginAdmin";
    private static final String IS_LOGIN_USER = "isloginUser";
    private static final String ADMIN_ID = "adminId";
    private static final String USER_ID = "userId";
    private static final String ADMIN_NAME = "adminName";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final int ADD_AMOUNT = 0;


    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    public Utils() {

    }

    public static void setIsLoginAdmin(Context context, String status) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(IS_LOGIN_ADMIN, status);
        editor.commit();
    }

    public static String getIsLoginAdmin(Context context) {

        String status;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        status = sharedPreferences.getString(IS_LOGIN_ADMIN, "0");

        return status;
    }

    public static void setIsLoginUser(Context context, String status) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(IS_LOGIN_USER, status);
        editor.commit();
    }

    public static String getIsLoginUser(Context context) {

        String status;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        status = sharedPreferences.getString(IS_LOGIN_USER, "0");

        return status;
    }

    public static void setAdminId(Context context, String saveId) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(ADMIN_ID, saveId);
        editor.commit();


    }


    public static String getAdminId(Context context) {
        String saveId;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        saveId = sharedPreferences.getString(ADMIN_ID, "0");

        return saveId;


    }

    public static void setAdminName(Context context, String saveId) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(ADMIN_NAME, saveId);
        editor.commit();


    }


    public static String getAdminName(Context context) {
        String saveId;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        saveId = sharedPreferences.getString(ADMIN_NAME, "0");

        return saveId;


    }

    public static void setUserId(Context context, String saveUserId) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(USER_ID, saveUserId);
        editor.commit();


    }


    public static String getUserId(Context context) {
        String saveUserId;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        saveUserId = sharedPreferences.getString(USER_ID, "0");

        return saveUserId;


    }

    public static void setIsLoggedIn(Context context, String status) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(IS_LOGGED_IN, status);
        editor.commit();
    }

    public static String getIsLoggedIn(Context context) {

        String status;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        status = sharedPreferences.getString(IS_LOGGED_IN, "0");

        return status;
    }

    public static void setAddAmount(Context context, int status) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("" + ADD_AMOUNT, status);
        editor.commit();
    }

    public static int getAddAmount(Context context) {

        int status;

        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        status = sharedPreferences.getInt("" + ADD_AMOUNT, 0);

        return status;
    }
}
