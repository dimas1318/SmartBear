package com.example.android.smartbear.session;

import com.example.android.smartbear.user.UserModel;

/**
 * Created by parsh on 22.10.2017.
 */

public interface SessionManager {
    void createUserSession(String email, String password, String name, Boolean isAdmin);
    boolean checkLogin();
    UserModel getUserDetails();
    void logoutUser();
}
