package com.example.android.smartbear;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by parsh on 21.10.2017.
 */

public class SessionManager {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREFERENCE_FILE_KEY = "KEY";
    private static final String IS_USER_LOGIN = "USER_LOGGED_IN";
    public static final String EMAIL_KEY = "EMAIL";
    public static final String NAME_KEY = "NAME";
    private static final String PASSWORD_KEY = "PASSWORD";
    public static final String ADMIN_KEY = "ADMIN";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    //Create login session
    public void createUserSession(String email, String password, String name, Boolean isAdmin) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(NAME_KEY, name);
        editor.putBoolean(ADMIN_KEY, isAdmin);
        editor.commit();
    }

    /**
     * Check user login status
     * If false it will redirect user to login page
     * @return login status
     */
    public boolean checkLogin() {
        if(!this.isUserLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Starting Login Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }

    /**
     * Get stored session credentials
     * @return user model
     */
    public UserModel getUserDetails(){
        String name = preferences.getString(NAME_KEY, null);
        String email = preferences.getString(EMAIL_KEY, null);
        Boolean isAdmin = preferences.getBoolean(ADMIN_KEY, false);
        UserModel user = new UserModel(name, email, isAdmin);
        return user;
    }

    public void logoutUser() {
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        context.startActivity(i);
    }

    private boolean isUserLoggedIn() {
        return preferences.getBoolean(IS_USER_LOGIN, false);
    }
}


