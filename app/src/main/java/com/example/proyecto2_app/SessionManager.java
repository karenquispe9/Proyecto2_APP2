package com.example.proyecto2_app;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    // Nombres de las preferencias
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_REMEMBER_ME = "rememberMe";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_TYPE = "userType";

    private SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void createLoginSession(int userId, String name, String email, String userType, boolean rememberMe) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_TYPE, userType);
        editor.apply();
    }

    public boolean isLoggedIn() {
        if (!sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            return false;
        }
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public boolean shouldRememberMe() {
        return sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
    }

    public String getProfileImageUrl() {
        return sharedPreferences.getString("profileImageUrl", "");
    }

    public void setProfileImageUrl(String url) {
        editor.putString("profileImageUrl", url);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserType() {
        return sharedPreferences.getString(KEY_USER_TYPE, null);
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}