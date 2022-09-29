package com.example.damstatusmonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


//here for this class we are using a singleton pattern

public class LOGSharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "sharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";

    private static LOGSharedPrefManager mInstance;
    private static Context mCtx;

    private LOGSharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized LOGSharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LOGSharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(LOGUser LOGUser) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, LOGUser.getId());
        editor.putString(KEY_USERNAME, LOGUser.getUsername());
        editor.putString(KEY_EMAIL, LOGUser.getEmail());
        //editor.putString(KEY_GENDER, LOGUser.getGender());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public LOGUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LOGUser(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
